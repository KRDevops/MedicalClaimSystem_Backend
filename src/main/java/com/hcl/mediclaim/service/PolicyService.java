package com.hcl.mediclaim.service;

import com.hcl.mediclaim.dto.ValidatePolicyResponseDto;
import com.hcl.mediclaim.exception.PolicyNotPresentException;

public interface PolicyService {

	ValidatePolicyResponseDto policyValidation(Long policyNumber) throws PolicyNotPresentException;

}
