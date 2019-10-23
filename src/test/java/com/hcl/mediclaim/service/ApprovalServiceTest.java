package com.hcl.mediclaim.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.hcl.mediclaim.dto.ApprovalDto;
import com.hcl.mediclaim.dto.ApprovalResponseDto;
import com.hcl.mediclaim.entity.Claim;
import com.hcl.mediclaim.entity.Hospital;
import com.hcl.mediclaim.entity.Policy;
import com.hcl.mediclaim.entity.Role;
import com.hcl.mediclaim.entity.User;
import com.hcl.mediclaim.exception.ApproverNotFoundException;
import com.hcl.mediclaim.repository.ClaimRepository;
import com.hcl.mediclaim.repository.HospitalRepository;
import com.hcl.mediclaim.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class ApprovalServiceTest {
	@Mock
	ClaimRepository claimRepository;
	@Mock
	HospitalRepository hospitalRepository;
	@InjectMocks
	ApprovalServiceImpl approvalService;
	@Mock
	UserRepository userRepository;

	@Test
	public void testApprove() throws ApproverNotFoundException
	{
	ApprovalResponseDto dto=new ApprovalResponseDto();
	dto.setStatusCode(200);
	Pageable pageable =PageRequest.of(1,1);
	User user=new User();
	user.setUserId(2l);
	Role role=new Role();
	role.setRoleId(2l);
	user.setRoleId(role);
	Policy policy=new Policy();
	policy.setPolicyNumber(1l);
	Claim claim=new Claim();
	claim.setApproverId(user);
	   claim.setUserId(user);
	   Hospital hos=new Hospital();
	   hos.setHospitalId(1l);
	   hos.setHospitalName("kg");
	   List<Hospital> hosp=new ArrayList<>();
	   hosp.add(hos);
	   claim.setHospitalId(hos);
	   claim.setPolicyNumber(policy);
	   claim.setClaimId(1l);
	   claim.setSeniorApproverId(user);
	Long approverId=2l;
	List<Claim> claims= new ArrayList<>();
	claims.add(claim);
	Mockito.when(claimRepository.findByApproverId(Mockito.any(),Mockito.any())).thenReturn(Optional.of(claims));
	Mockito.when(userRepository.findByUserId(approverId)).thenReturn(Optional.of(user));
	Mockito.when(hospitalRepository.findAll()).thenReturn(hosp);
	List<ApprovalDto> response=approvalService.approve(2l,0);
	assertEquals(response.size(),claims.size());
	}
	@Test
	public void testApprovalSeniorLevel() throws ApproverNotFoundException{
	ApprovalResponseDto dto=new ApprovalResponseDto();
	dto.setStatusCode(200);
	Pageable pageable =PageRequest.of(1,1);
	User user=new User();
	user.setUserId(2l);
	Role role=new Role();
	role.setRoleId(3l);
	user.setRoleId(role);
	Policy policy=new Policy();
	policy.setPolicyNumber(1l);
	Claim claim=new Claim();
	claim.setApproverId(user);
	   claim.setUserId(user);
	   Hospital hos=new Hospital();
	   hos.setHospitalId(1l);
	   hos.setHospitalName("kg");
	   List<Hospital> hosp=new ArrayList<>();
	   hosp.add(hos);
	   claim.setHospitalId(hos);
	   claim.setPolicyNumber(policy);
	   claim.setClaimId(1l);
	   claim.setSeniorApproverId(user);
	Long approverId=2l;
	List<Claim> claims= new ArrayList<>();
	claims.add(claim);
	Mockito.when(claimRepository.findBySeniorApproverId(Mockito.any(),Mockito.any())).thenReturn(Optional.of(claims));
	Mockito.when(userRepository.findByUserId(approverId)).thenReturn(Optional.of(user));
	Mockito.when(hospitalRepository.findAll()).thenReturn(hosp);
	List<ApprovalDto> response=approvalService.approve(2l,0);
	assertEquals(response.size(),claims.size());
	}
	}




