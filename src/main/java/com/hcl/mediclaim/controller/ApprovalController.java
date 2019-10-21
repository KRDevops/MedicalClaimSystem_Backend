package com.hcl.mediclaim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.mediclaim.service.ApprovalService;



@RestController
@RequestMapping(value="/api/v1")
public class ApprovalController {
	@Autowired
	ApprovalService approvalService;
	
	@GetMapping(value="/approvals/{approverId}")
	public ResponseEntity approval(@PathVariable Long approverId,@RequestParam Integer pageNumber){
		return new ResponseEntity(approvalService.approve(approverId,pageNumber),HttpStatus.OK);
		
	}
	

}
