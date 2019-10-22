package com.hcl.mediclaim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hcl.mediclaim.exception.ApproverNotFoundException;
import com.hcl.mediclaim.service.ApprovalService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author SaiCharan
 */

@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RequestMapping(value = "/api/v1")
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
	public ResponseEntity approval(@PathVariable Long approverId, @RequestParam Integer pageNumber) throws ApproverNotFoundException {
		log.info("entered into approval controller");
		return new ResponseEntity(approvalService.approve(approverId, pageNumber), HttpStatus.OK);

	}

}
