package com.hcl.mediclaim.controller;

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

import com.hcl.mediclaim.dto.ApproveRequestDto;
import com.hcl.mediclaim.dto.ResponseDto;
import com.hcl.mediclaim.exception.MediClaimException;
import com.hcl.mediclaim.service.ApprovalService;
import com.hcl.mediclaim.util.MediClaimUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@Slf4j
public class ApprovalController {
	@Autowired
	ApprovalService approvalService;

	@GetMapping(value = "/approvals/{approverId}")
	public ResponseEntity approval(@PathVariable Long approverId, @RequestParam Integer pageNumber) {
		return new ResponseEntity(approvalService.approve(approverId, pageNumber), HttpStatus.OK);

	}

	@PutMapping("/approvals")
	public ResponseEntity approve(@RequestBody ApproveRequestDto approveRequestDto)
			throws MediClaimException, MessagingException {
		log.info("approve method in ApprovalController started");
		ResponseDto responseDto = approvalService.approve(approveRequestDto);
		log.info("approve method in ApprovalController ended");
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

}
