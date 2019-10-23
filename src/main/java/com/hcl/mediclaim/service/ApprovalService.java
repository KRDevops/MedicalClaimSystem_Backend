package com.hcl.mediclaim.service;

import javax.mail.MessagingException;

import com.hcl.mediclaim.dto.ApprovalResponseDto;
import com.hcl.mediclaim.dto.ApproveRequestDto;
import com.hcl.mediclaim.dto.ResponseDto;
import com.hcl.mediclaim.exception.MediClaimException;

public interface ApprovalService {

	ApprovalResponseDto viewClaimRequests(Long approverId, Integer pageNumber);

	ResponseDto approveOrReject(ApproveRequestDto approveRequestDto) throws MediClaimException, MessagingException;

}
