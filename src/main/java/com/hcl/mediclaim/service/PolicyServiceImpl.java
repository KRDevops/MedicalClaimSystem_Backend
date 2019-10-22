package com.hcl.mediclaim.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.mediclaim.dto.ValidatePolicyResponseDto;
import com.hcl.mediclaim.entity.Policy;
import com.hcl.mediclaim.exception.PolicyNotPresentException;
import com.hcl.mediclaim.repository.PolicyRepository;
import com.hcl.mediclaim.repository.UserRepository;
import com.hcl.mediclaim.util.MediClaimUtil;

/**
 * 
 * @author User1
 *
 * @since 2019-10-21 This class includes functionality for validating if the
 *        policy number is present or not.
 */

@Service
public class PolicyServiceImpl implements PolicyService {

	@Autowired
	PolicyRepository policyRepository;

	@Autowired
	UserRepository userRepository;

	/**
	 * Validating the policy number if it is present in backend or not.
	 * 
	 * @param policyNumber
	 * @return {@link ValidatePolicyResponseDto}
	 * @throws PolicyNotPresentException
	 */
	@Override
	public ValidatePolicyResponseDto policyValidation(Long policyNumber) throws PolicyNotPresentException {
		Optional<Policy> policy = policyRepository.findById(policyNumber);
		if (policy.isPresent()) {
			ValidatePolicyResponseDto policyResponseDto = new ValidatePolicyResponseDto();
			policyResponseDto.setAvailableAmount(policy.get().getAvailableAmount());
			policyResponseDto.setEntitledAmount(policy.get().getEntitledAmount());
			policyResponseDto.setExpiryDate(policy.get().getExpiryDate());
			policyResponseDto.setPolicyName(policy.get().getPolicyName());
			policyResponseDto.setStatus(MediClaimUtil.ACTIVE);
			policyResponseDto.setUserId(policy.get().getUserId().getUserId());
			return policyResponseDto;
		}else {
			throw new PolicyNotPresentException(MediClaimUtil.POLICY_NOT_FOUND);
		}
	}

}
