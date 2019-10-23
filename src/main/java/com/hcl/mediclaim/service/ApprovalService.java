package com.hcl.mediclaim.service;

import java.util.List;

import javax.mail.MessagingException;

import com.hcl.mediclaim.dto.ApprovalDto;
import com.hcl.mediclaim.dto.ApproveRequestDto;
import com.hcl.mediclaim.dto.ResponseDto;
import com.hcl.mediclaim.exception.ApproverNotFoundException;
import com.hcl.mediclaim.exception.MediClaimException;

public interface ApprovalService {

	List<ApprovalDto> viewApprovals(Long approverId, Integer pageNumber) throws ApproverNotFoundException;

	ResponseDto approveOrReject(ApproveRequestDto approveRequestDto) throws MediClaimException, MessagingException;

}
