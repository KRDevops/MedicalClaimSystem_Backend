package com.hcl.mediclaim.service;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.hcl.mediclaim.dto.ValidatePolicyResponseDto;
import com.hcl.mediclaim.repository.PolicyRepository;
import com.hcl.mediclaim.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class PolicyServiceTest {
	
	@Mock
	PolicyService policyService;
	
	@InjectMocks
	PolicyServiceImpl policyServiceImpl;
	
	@Mock
	UserRepository userRepository;
	
	PolicyRepository policyRepository;
	
	ValidatePolicyResponseDto policyResponseDto=new ValidatePolicyResponseDto();
	
	@Before
	public void setup() {
		policyResponseDto.setAvailableAmount(500);
		policyResponseDto.setEntitledAmount(650);
		policyResponseDto.setExpiryDate(LocalDate.of(2019, 10, 16));
		policyResponseDto.setMessage("Success");
		policyResponseDto.setPolicyName("Approver policy");
		policyResponseDto.setStatus("senior Approval");
		policyResponseDto.setStatusCode(201);
		policyResponseDto.setUserId(1L);
		
		}
	
	@Test
	public void policyTest()
	{
		
		
	}
	
	
	
	

}
