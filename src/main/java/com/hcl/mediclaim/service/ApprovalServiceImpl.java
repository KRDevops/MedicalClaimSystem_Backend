package com.hcl.mediclaim.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hcl.mediclaim.dto.ApprovalDto;
import com.hcl.mediclaim.dto.ApproveRequestDto;
import com.hcl.mediclaim.dto.ResponseDto;
import com.hcl.mediclaim.entity.Claim;
import com.hcl.mediclaim.entity.Hospital;
import com.hcl.mediclaim.entity.Policy;
import com.hcl.mediclaim.entity.Role;
import com.hcl.mediclaim.entity.User;
import com.hcl.mediclaim.exception.ApproverNotFoundException;
import com.hcl.mediclaim.exception.MediClaimException;
import com.hcl.mediclaim.repository.ClaimRepository;
import com.hcl.mediclaim.repository.HospitalRepository;
import com.hcl.mediclaim.repository.PolicyRepository;
import com.hcl.mediclaim.repository.RoleRepository;
import com.hcl.mediclaim.repository.UserRepository;
import com.hcl.mediclaim.util.JavaMailUtil;
import com.hcl.mediclaim.util.MediClaimUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApprovalServiceImpl implements ApprovalService {
	@Autowired
	ClaimRepository claimRepository;
	@Autowired
	HospitalRepository hospitalRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PolicyRepository policyRepository;

	@Autowired
	JavaMailUtil javaMailUtil;

	/**
	 * This method is used to fetch the approvals list for the loggedIn approver
	 * 
	 * @param approverId
	 * @param pageNumber
	 * @return ApprovalRepsoneDto
	 * @throws ApproverNotFoundException
	 */
	@Override
	public List<ApprovalDto> approve(Long approverId, Integer pageNumber) throws ApproverNotFoundException {
		log.info("entered into approvalService");
		User user = new User();
		user.setUserId(approverId);
		Pageable paging = PageRequest.of(pageNumber, MediClaimUtil.SIZE);
		Optional<User> user1 = userRepository.findByUserId(approverId);
		if (!user1.isPresent()) {
			throw new ApproverNotFoundException(MediClaimUtil.APPROVERNOT_FOUND);
		}
		List<Claim> claim1 = new ArrayList<>();
		Optional<List<Claim>> claims = Optional.of(claim1);
		
		List<ApprovalDto> approvalDtos = new ArrayList<>();
		List<Hospital> hospitals = hospitalRepository.findAll();
		if (user1.get().getRoleId().getRoleId().equals(MediClaimUtil.TWO)) {
			claims = claimRepository.findByApproverId(user, paging);

		} else {
			claims = claimRepository.findBySeniorApproverId(user, paging);
		}
		if (!claims.isPresent()) {
			throw new ApproverNotFoundException(MediClaimUtil.APPROVER_NOT_FOUND);
		}
		claims.get().forEach(claim -> {
			ApprovalDto approvalDto = new ApprovalDto();
			BeanUtils.copyProperties(claim, approvalDto);
			hospitals.forEach(hospital -> {
				if (hospital.getHospitalId().equals(claim.getHospitalId().getHospitalId())) {
					approvalDto.setHospitalName(hospital.getHospitalName());
				}
			});
			approvalDto.setUserId(claim.getUserId().getUserId());
			approvalDto.setPolicyNumber(claim.getPolicyNumber().getPolicyNumber());
			approvalDtos.add(approvalDto);
		});
		return approvalDtos;
	}

	/**
	 * @throws MediClaimException
	 * @throws MessagingException
	 * 
	 */
	@Override
	public ResponseDto approve(ApproveRequestDto approveRequestDto) throws MediClaimException, MessagingException {
		log.info("approve method in Approval Service started");
		Optional<User> approver = userRepository.findByUserId(approveRequestDto.getApproverId());
		Optional<Claim> claim = claimRepository.findByClaimId(approveRequestDto.getClaimId());
		Policy policy = new Policy();
		ResponseDto responseDto = new ResponseDto();
		Optional<List<User>> seniorApprovers = userRepository.findByRoleId(
				new Role(MediClaimUtil.THREE, MediClaimUtil.SENIOR_APPROVER_ROLE, MediClaimUtil.SENIOR_APPROVER_ROLE));
		if (claim.isPresent()) {
			if (approver.get().getRoleId().getRoleId().equals(MediClaimUtil.TWO)
					&& approveRequestDto.getStatus().equals(MediClaimUtil.APPROVE)) {
				if (claim.get().getDeviationPercentage() < MediClaimUtil.TEN) {
					claimRepository.updateClaimStatusAndRemarksByClaimId(approveRequestDto.getClaimId(),
							approveRequestDto.getStatus(), approveRequestDto.getRemarks());
					BeanUtils.copyProperties(claim.get().getPolicyNumber(), policy);
					if (claim.get().getPolicyNumber().getAvailableAmount() > claim.get().getClaimAmount()) {
						policy.setAvailableAmount(
								claim.get().getPolicyNumber().getAvailableAmount() - claim.get().getClaimAmount());
						policyRepository.save(policy);
					} else {
						throw new MediClaimException(MediClaimUtil.AVAILABLE_LOW_EXCEPTION);
					}
					javaMailUtil.sendEmail(claim.get().getUserId().getEmailId(), MediClaimUtil.USER_APPROVED_MESSAGE,
							MediClaimUtil.APPROVED);
					log.info("First level Approval is successful");
					responseDto.setMessage(MediClaimUtil.APPROVED);
					responseDto.setStatusCode(MediClaimUtil.GENERICSUCCESSCODE);
				} else {
					responseDto.setMessage(MediClaimUtil.MOVE);
					responseDto.setStatusCode(MediClaimUtil.GENERICSUCCESSCODE);
				}
			} else if (approver.get().getRoleId().getRoleId().equals(MediClaimUtil.TWO)
					&& approveRequestDto.getStatus().equals(MediClaimUtil.REJECT)) {
				claimRepository.updateClaimStatusAndRemarksByClaimId(approveRequestDto.getClaimId(),
						approveRequestDto.getStatus(), approveRequestDto.getRemarks());
				responseDto.setMessage(MediClaimUtil.REJECTED);
				responseDto.setStatusCode(MediClaimUtil.GENERICSUCCESSCODE);
				javaMailUtil.sendEmail(claim.get().getUserId().getEmailId(), MediClaimUtil.USER_REJECTED_MESSAGE,
						MediClaimUtil.REJECTED);
			} else if (approveRequestDto.getStatus().equals(MediClaimUtil.PASS)) {

				Optional<User> seniorApprover = seniorApprovers.get().stream().findAny();
				if (seniorApprover.isPresent()) {
					claimRepository.updateClaimStatusAndSeniorApproverIdAndRemarksByClaimId(seniorApprover.get(),
							approveRequestDto.getClaimId(), MediClaimUtil.PENDING, approveRequestDto.getRemarks());
				} else {
					throw new MediClaimException(MediClaimUtil.SENIOR_APPROVER_NOT_PRESENT);
				}
				responseDto.setMessage(MediClaimUtil.PASS);
				responseDto.setStatusCode(MediClaimUtil.GENERICSUCCESSCODE);
			}

			if (approver.get().getRoleId().getRoleId().equals(MediClaimUtil.THREE)) {
				claimRepository.updateClaimStatusAndRemarksByClaimId(approveRequestDto.getClaimId(),
						approveRequestDto.getStatus(), approveRequestDto.getRemarks());

				if (approveRequestDto.getStatus().equals(MediClaimUtil.APPROVE)) {
					javaMailUtil.sendEmail(claim.get().getUserId().getEmailId(), MediClaimUtil.USER_APPROVED_MESSAGE,
							MediClaimUtil.APPROVED);
					policy.setAvailableAmount(
							claim.get().getPolicyNumber().getAvailableAmount() - claim.get().getClaimAmount());
					policyRepository.save(policy);
				} else if (approveRequestDto.getStatus().equals(MediClaimUtil.REJECT)) {
					javaMailUtil.sendEmail(claim.get().getUserId().getEmailId(), MediClaimUtil.USER_REJECTED_MESSAGE,
							MediClaimUtil.REJECTED);
				}

				responseDto.setMessage(MediClaimUtil.APRROVEORREJECTBYSENIOR);
				responseDto.setStatusCode(MediClaimUtil.GENERICSUCCESSCODE);
				log.info("Senior level Approval is successful");
			}
		}
		log.info("approve method in Approval Service ended");
		return responseDto;
	}

}
