package com.hcl.mediclaim.service;

import com.hcl.mediclaim.dto.ApprovalResponseDto;
import com.hcl.mediclaim.dto.ApproveRequestDto;
import com.hcl.mediclaim.dto.ResponseDto;
import com.hcl.mediclaim.exception.MediClaimException;

public interface ApprovalService {

	ApprovalResponseDto approve(Long approverId, Integer pageNumber);

	ResponseDto approve(ApproveRequestDto approveRequestDto) throws MediClaimException;

}
