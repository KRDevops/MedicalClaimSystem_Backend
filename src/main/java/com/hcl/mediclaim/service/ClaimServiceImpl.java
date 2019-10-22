package com.hcl.mediclaim.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hcl.mediclaim.dto.ClaimRequestDto;
import com.hcl.mediclaim.dto.ClaimResponseDto;
import com.hcl.mediclaim.entity.Claim;
import com.hcl.mediclaim.entity.Hospital;
import com.hcl.mediclaim.entity.Policy;
import com.hcl.mediclaim.entity.Role;
import com.hcl.mediclaim.entity.User;
import com.hcl.mediclaim.enums.RoleNames;
import com.hcl.mediclaim.exception.MediClaimException;
import com.hcl.mediclaim.repository.ClaimRepository;
import com.hcl.mediclaim.repository.HospitalRepository;
import com.hcl.mediclaim.repository.PolicyRepository;
import com.hcl.mediclaim.repository.RoleRepository;
import com.hcl.mediclaim.repository.UserRepository;
import com.hcl.mediclaim.util.JavaMailUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author User1
 *
 */
@Service
@Slf4j
public class ClaimServiceImpl implements ClaimService {

	@Autowired
	ClaimRepository claimRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PolicyRepository policyRepository;

	@Autowired
	HospitalRepository hospitalRepository;

	@Autowired
	JavaMailUtil javaMailUtil;

	/**
	 * @throws IOException
	 * @throws MediClaimException
	 * @throws MessagingException 
	 * 
	 */
	@Override
	public ClaimResponseDto create(MultipartFile documents, String claimRequestDto)
			throws IOException, MediClaimException, MessagingException {

		Claim claim = new Claim();
		Double deviationPercent = 0.00;

		ClaimResponseDto claimResponseDto = new ClaimResponseDto();

		// Converting String to Object
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		ClaimRequestDto claimRequest = objectMapper.readValue(claimRequestDto, ClaimRequestDto.class);

		Optional<User> user = userRepository.findById(claimRequest.getUserId());
		Optional<Policy> policy = policyRepository.findById(claimRequest.getPolicyNumber());
		Optional<Hospital> hospital = hospitalRepository.findById(claimRequest.getHospitalId());

		if (!user.isPresent()) {
			throw new MediClaimException("Invalid User");
		}

		if (!policy.isPresent()) {
			throw new MediClaimException("Invalid Policy Number");
		}

		if (!hospital.get().getCountry().equalsIgnoreCase("India")) {
			throw new MediClaimException("Provided Hospital Is Outside Coverage");
		}

		Optional<User> approver=assignApprover(RoleNames.APPROVER);

		// Calculating Deviation Amount
		if (claimRequest.getClaimAmount() > policy.get().getAvailableAmount()) {
			deviationPercent = (claimRequest.getClaimAmount() - policy.get().getAvailableAmount()) * 100
					/ policy.get().getAvailableAmount();
		}

		// Copying File To Resource Folder
		Path rootLocation = Paths.get("src/main/resources");
		String fileName = claimRequest.getPolicyNumber().toString() + claimRequest.getUserId().toString() + user.get().getUserName()+".pdf";
		Files.copy(documents.getInputStream(), rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

		// Setting Values
		BeanUtils.copyProperties(claimRequest, claim);
		claim.setHospitalId(hospital.get());
		claim.setPolicyNumber(policy.get());
		claim.setApproverId(approver.get());
		claim.setUserId(user.get());
		claim.setDocumentName(fileName);
		claim.setDeviationPercentage(deviationPercent.intValue());
		// Saving To Repository
		Claim claimResponse = claimRepository.save(claim);

		claimResponseDto.setClaimId(claimResponse.getClaimId());
		
		//Sending Mail To User And Approver
		javaMailUtil.sendEmail(user.get().getEmailId(), "Hi ,\n Your Claim Request Has Been Submitted Successfully \n ", "MEDICLAIM STATUS");
		javaMailUtil.sendEmail(approver.get().getEmailId(), "Hi,\n A Mediclaim Request is waiting for your approval \n", "MEDICLAIM APPROVAL REQUEST");

		return claimResponseDto;
	}

	public Optional<User> assignApprover(RoleNames roleName) {
		Optional<Role> role = roleRepository.findByRoleName(roleName.name());
		
		Optional<List<User>> userList = Optional.of(new ArrayList<User>());
		if (role.isPresent()) {
			userList = userRepository.findByRoleId(role.get());
		}
		Optional<User> approver = userList.get().stream().findAny();
		return approver;
	}

}
