package com.hcl.mediclaim.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hcl.mediclaim.dto.ApprovalDto;
import com.hcl.mediclaim.dto.ApprovalResponseDto;
import com.hcl.mediclaim.entity.Claim;
import com.hcl.mediclaim.entity.Hospital;

import com.hcl.mediclaim.entity.User;
import com.hcl.mediclaim.repository.ClaimRepository;
import com.hcl.mediclaim.repository.HospitalRepository;

@Service
public class ApprovalServiceImpl implements ApprovalService {
	@Autowired
	ClaimRepository claimRepository;
	@Autowired
	HospitalRepository hospitalRepository;

	@Override
	public ApprovalResponseDto approve(Long approverId, Integer pageNumber) {

		User user = new User();
		user.setUserId(approverId);
		
		Pageable paging = PageRequest.of(pageNumber, 3);
	
		
		List<Claim> claim4 = claimRepository.findByApproverId(user);
		List<Claim> claims = claimRepository.findByApproverId(user, paging);

		List<Hospital> hos = hospitalRepository.findAll();
		System.out.println(claims.size());
		List<ApprovalDto> dto = new ArrayList<>();
		ApprovalResponseDto dtos = new ApprovalResponseDto();
		claims.forEach(claim1 -> {
			ApprovalDto dto1 = new ApprovalDto();

			dto1.setUserId(claim1.getUserId().getUserId());
			dto1.setAdmissionDate(claim1.getAdmissionDate());
			dto1.setClaimAmount(claim1.getClaimAmount());
			dto1.setClaimDate(claim1.getClaimDate());
			dto1.setClaimId(claim1.getClaimId());
			dto1.setClaimStatus(claim1.getClaimStatus());
			dto1.setDeviationPercent(claim1.getDeviationPercentage());
			dto1.setDiagnosis(claim1.getDiagnosis());
			dto1.setDischargeDate(claim1.getDischargeDate());
			hos.forEach(hos1 -> {
				if (claim1.getHospitalId() == claim1.getHospitalId()) {
					dto1.setHospitalName(hos1.getHospitalName());
				}
			});
			dto1.setDocuments(claim1.getDocuments());
			dto1.setPolicyNumber(claim1.getPolicyNumber().getPolicyNumber());
			dto1.setRemarks(claim1.getRemarks());
			dto.add(dto1);
			dtos.setClaim(dto);
		});
		dtos.setCount(claim4.size());
		dtos.setMessage("SUCCESS");
		dtos.setStatusCode(200);
		return dtos;

	}

}
