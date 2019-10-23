package com.hcl.mediclaim.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.mediclaim.dto.ApprovalDto;
import com.hcl.mediclaim.dto.ApprovalResponseDto;
import com.hcl.mediclaim.dto.ApproveRequestDto;
import com.hcl.mediclaim.dto.ResponseDto;
import com.hcl.mediclaim.exception.ApproverNotFoundException;
import com.hcl.mediclaim.exception.MediClaimException;
import com.hcl.mediclaim.service.ApprovalService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@Slf4j
public class ApprovalController {
	@Autowired
	ApprovalService approvalService;

	/**
	 * This method is used to fetch the approvals list for the loggedIn approver
	 * 
	 * @param approverId
	 * @param pageNumber
	 * @return ApprovalRepsoneDto
	 * @throws ApproverNotFoundException
	 *
	 */
	@GetMapping(value = "/approvals/{approverId}")
	public ResponseEntity<ApprovalResponseDto> view(@PathVariable Long approverId, @RequestParam Integer pageNumber)
			throws ApproverNotFoundException {
		log.info("entered into approval controller");
		List<ApprovalDto> approvalDto = approvalService.viewApprovals(approverId, pageNumber);
		ApprovalResponseDto approvalResponseDto = new ApprovalResponseDto();
		approvalResponseDto.setClaim(approvalDto);
		approvalResponseDto.setMessage("success");
		approvalResponseDto.setStatusCode(200);
		return new ResponseEntity<>(approvalResponseDto, HttpStatus.OK);

	}

	/**
	 * This method is used to review and approve/reject the claims submitted by
	 * users
	 * 
	 * @param approveRequestDto
	 * @return {@link ResponseEntity}
	 * @throws MediClaimException
	 * @throws MessagingException
	 */
	@PutMapping("/approvals")
	public ResponseDto review(@RequestBody ApproveRequestDto approveRequestDto)
			throws MediClaimException, MessagingException {
		log.info("approve method in ApprovalController started");
		ResponseDto responseDto = approvalService.approveOrReject(approveRequestDto);
		log.info("approve method in ApprovalController ended");
		return responseDto;
	}

}
