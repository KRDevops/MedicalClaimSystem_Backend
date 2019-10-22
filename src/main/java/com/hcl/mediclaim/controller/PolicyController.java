package com.hcl.mediclaim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.mediclaim.dto.ValidatePolicyResponseDto;
import com.hcl.mediclaim.exception.PolicyNotPresentException;
import com.hcl.mediclaim.service.PolicyService;
import com.hcl.mediclaim.util.MediClaimUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ratna
 * @since 2019-10-21 this includes methods  to validate the policy number
 *
 */
@RestController
@RequestMapping("/cms")
@Slf4j
public class PolicyController {

	@Autowired
	PolicyService policyService;

	/**
	 * 
	 * @param policyNumber
	 * @return
	 *     method is used to validate policy number for the policy functionality in mediclaim
	 *     management system.
	 * @throws PolicyNotPresentException
	 */

	@GetMapping("api/v1/policies/{policyNumber}")
	public ResponseEntity<ValidatePolicyResponseDto> policyValidation(@PathVariable("policyNumber") Long policyNumber)
			throws PolicyNotPresentException {
		log.info("policyValidation in Policy controller started");
		ValidatePolicyResponseDto policyResponseDto = policyService.policyValidation(policyNumber);
		policyResponseDto.setMessage(MediClaimUtil.SUCCESS);
		policyResponseDto.setStatusCode(MediClaimUtil.GENERICSUCCESSCODE);
		log.info("policyValidation in Policy controller ended");
		return new ResponseEntity<>(policyResponseDto, HttpStatus.FOUND);
	}

}
