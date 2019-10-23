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

import org.apache.commons.io.FilenameUtils;
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
import com.hcl.mediclaim.enums.Ailment;
import com.hcl.mediclaim.enums.RoleNames;
import com.hcl.mediclaim.exception.MediClaimException;
import com.hcl.mediclaim.repository.ClaimRepository;
import com.hcl.mediclaim.repository.HospitalRepository;
import com.hcl.mediclaim.repository.PolicyRepository;
import com.hcl.mediclaim.repository.RoleRepository;
import com.hcl.mediclaim.repository.UserRepository;
import com.hcl.mediclaim.util.JavaMailUtil;
import com.hcl.mediclaim.util.MediClaimUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Afrin
 * @since 2019-10-22 This class includes methods for raising a mediclaim
 *        request.
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
	 * This method will save per claim details into the respective table.
	 * 
	 * @param documents contain the file data
	 * @param claimRequestDto conatins
	 *                        diagnosis,admissionDate,dischargeDate,hospitalId,policyNumber,natureOfAilment,userId,claimAmount.
	 * @throws IOException thrown when interrupted I/O Operations
	 * @throws MediClaimException Custom Exceptions
	 * @throws MessagingException thrown when mail sending fails
	 * @return ClaimResponseDto contains claim id,message and status code.
	 * 
	 */
	@Override
	public ClaimResponseDto create(MultipartFile documents, String claimRequestDto)
			throws IOException, MediClaimException, MessagingException {

		log.info("Create Method In Claim Service Started");

		Claim claim = new Claim();
		Double deviationPercent = 0.00;
		Optional<User> approver = Optional.empty();

		ClaimResponseDto claimResponseDto = new ClaimResponseDto();

		// Converting String to ClaimRequestDto Object
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		ClaimRequestDto claimRequest = objectMapper.readValue(claimRequestDto, ClaimRequestDto.class);

		Optional<User> user = userRepository.findById(claimRequest.getUserId());
		Optional<Policy> policy = policyRepository.findById(claimRequest.getPolicyNumber());
		Optional<Hospital> hospital = hospitalRepository.findById(claimRequest.getHospitalId());
		
		//Check File Format
		if(!FilenameUtils.getExtension(documents.getOriginalFilename()).equalsIgnoreCase(MediClaimUtil.FILE_EXTENSION)) {
			throw new MediClaimException(MediClaimUtil.FILE_FORMAT_INVALID);
		}
		if (!user.isPresent()) {
			throw new MediClaimException(MediClaimUtil.USER_NOT_FOUND);
		}

		if (!policy.isPresent()) {
			throw new MediClaimException(MediClaimUtil.POLICY_NOT_FOUND);
		}
		//Check Network Coverage
		if (hospital.isPresent() && !hospital.get().getCountry().equalsIgnoreCase(MediClaimUtil.COUNTRY)) {
			throw new MediClaimException(MediClaimUtil.HOSPITAL_NETWORK);
		}
		
		if(!claimRequest.getAdmissionDate().isBefore(claimRequest.getDischargeDate())) {
			throw new MediClaimException(MediClaimUtil.INVALID_DATE_DIFFERENCE);
		}
		
		// Getting Approver List
		Optional<Role> role = roleRepository.findByRoleName(RoleNames.APPROVER.name());

		Optional<List<User>> approverList = Optional.of(new ArrayList<User>());
		
		if (role.isPresent()) {
			approverList = userRepository.findByRoleId(role.get());
		}
		
		// Calculating Deviation Percentage
		if (claimRequest.getClaimAmount() > policy.get().getAvailableAmount()) {
			deviationPercent = (claimRequest.getClaimAmount() - policy.get().getAvailableAmount()) * 100
					/ policy.get().getAvailableAmount();
		}
		
		// Copying File To Resource Folder
		Path rootLocation = Paths.get(MediClaimUtil.ATTACHMENT_PATH);
		String fileName = user.get().getUserName() + claimRequest.getPolicyNumber().toString()
				+ claimRequest.getUserId().toString() + "." + MediClaimUtil.FILE_EXTENSION;
		Files.copy(documents.getInputStream(), rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

		// Setting Values
		BeanUtils.copyProperties(claimRequest, claim);
		claim.setPolicyNumber(policy.get());
		claim.setUserId(user.get());
		claim.setDocumentName(fileName);
		claim.setDeviationPercentage(deviationPercent.intValue());
		
		if (hospital.isPresent()) {
			claim.setHospitalId(hospital.get());
		}
		
		claim.setNatureOfAilment(Ailment.valueOf(claimRequest.getNatureOfAilment()));
		
		//Picking Random Approver From List
		if (approverList.isPresent()) {
			User approverId=claimRepository.findTopByOrderByClaimIdDesc().getApproverId();
			approverList.get().remove(approverId);
			approver = approverList.get().stream().findAny();
		}

		if (approver.isPresent()) {
			claim.setApproverId(approver.get());
		}

		// Saving To Repository
		Claim claimResponse = claimRepository.save(claim);

		claimResponseDto.setClaimId(claimResponse.getClaimId());

		// Sending Mail To User And Approver
		javaMailUtil.sendEmail(user.get().getEmailId(), MediClaimUtil.SUBMIT_USER_MESSAGE,
				MediClaimUtil.SUBMIT_USER_SUBJECT);

		if (approver.isPresent()) {
			javaMailUtil.sendEmail(approver.get().getEmailId(), MediClaimUtil.SUBMIT_APPROVER_MESSAGE,
					MediClaimUtil.SUBMIT_APPROVER_SUBJECT);
		}
		log.info("Created Method in Claim Service Ended");
		return claimResponseDto;
	}

}
