package com.hcl.mediclaim.service;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.hcl.mediclaim.dto.ValidatePolicyResponseDto;
import com.hcl.mediclaim.entity.Policy;
import com.hcl.mediclaim.entity.User;
import com.hcl.mediclaim.exception.PolicyNotPresentException;
import com.hcl.mediclaim.repository.PolicyRepository;

@RunWith(MockitoJUnitRunner.class)
public class PolicyServiceTest {
	
	@Mock
	PolicyRepository policyRepository;
	
	@InjectMocks
	PolicyServiceImpl policyServiceImpl;
	
	Policy policy=new Policy();
	ValidatePolicyResponseDto validatePolicyResponseDto=new ValidatePolicyResponseDto();
	User user=new User();
	
	Long policyNumber=1L;
	
	@Before
	public void setup() {
		user.setUserId(11L);
		policy.setPolicyNumber(1L);
		policy.setAvailableAmount(5500.00);
		policy.setEntitledAmount(6000.00);
		policy.setExpiryDate(LocalDate.of(2019, 10, 10));
		policy.setPolicyName("Jeevan Ratna");
		policy.setUserId(user);
	}
	
	@Test
	public void positivePolicyValidation() throws PolicyNotPresentException {
		Mockito.when(policyRepository.findById(Mockito.any())).thenReturn(Optional.of(policy));
		validatePolicyResponseDto=policyServiceImpl.policyValidation(policyNumber);
		Assert.assertEquals(11L, validatePolicyResponseDto.getUserId());
	}
	
	@Test(expected=PolicyNotPresentException.class)
	public void negativePolicyValidation() throws PolicyNotPresentException {
		Mockito.when(policyRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		validatePolicyResponseDto=policyServiceImpl.policyValidation(policyNumber);
	}
}

	