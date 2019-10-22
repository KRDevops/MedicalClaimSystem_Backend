package com.hcl.mediclaim.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import com.hcl.mediclaim.dto.ValidatePolicyResponseDto;
import com.hcl.mediclaim.entity.Policy;
import com.hcl.mediclaim.exception.PolicyNotPresentException;
import com.hcl.mediclaim.service.PolicyService;

@RunWith(MockitoJUnitRunner.class)
public class PolicyControllerTest {

	@Mock
	PolicyService policyService;

	@InjectMocks
	PolicyController policyController;

	@Mock
	ValidatePolicyResponseDto policyResponseDto = new ValidatePolicyResponseDto();

	Long policyNumber = 2L;

	Policy policy = new Policy();

	@Before
	public void setup() {

		policyResponseDto.setMessage("SUCCESS");
		policyResponseDto.setStatusCode(302);
		policy.setPolicyNumber(1L);

	}

	@Test
	public void validateTest() throws PolicyNotPresentException {
		Mockito.when(policyService.policyValidation(Mockito.any())).thenReturn(policyResponseDto);
		ResponseEntity<ValidatePolicyResponseDto> actual = policyController.policyValidation(policyNumber);
		Assert.assertEquals(302, actual.getStatusCode().value());

	}

}
