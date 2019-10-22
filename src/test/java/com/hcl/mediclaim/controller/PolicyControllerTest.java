package com.hcl.mediclaim.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.hcl.mediclaim.dto.ValidatePolicyResponseDto;
import com.hcl.mediclaim.exception.PolicyNotPresentException;
import com.hcl.mediclaim.service.PolicyService;

@RunWith(MockitoJUnitRunner.class)
public class PolicyControllerTest {
	
	@Mock
	PolicyService policyService;
	
	@InjectMocks
	PolicyController policyController;
	
	@Mock
	ValidatePolicyResponseDto policyResponseDto= new  ValidatePolicyResponseDto();
	
	@Before
	public void setup() {
		
		policyResponseDto.setMessage("SUCCESS");
		policyResponseDto.setStatusCode(201);
		
			}
	
	@Test
	public void validateTest() throws PolicyNotPresentException
	{
		/*Mockito.when(policyService.policyValidation(Mockito.anyLong())).thenReturn(policyResponseDto);
		//ValidatePolicyResponseDto actual=policyController.policyValidation(policyResponseDto);
		//Assert.assertEquals(policyResponseDto, actual);*/
	
		
		
	}
	
	
	

}
