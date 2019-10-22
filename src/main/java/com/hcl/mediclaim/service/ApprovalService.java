package com.hcl.mediclaim.service;

import org.springframework.stereotype.Service;

import com.hcl.mediclaim.dto.ApprovalResponseDto;

import com.hcl.mediclaim.exception.ApproverNotFoundException;

@Service
public interface ApprovalService {

	ApprovalResponseDto approve(Long approverId,Integer pageNumber) throws ApproverNotFoundException;

}
