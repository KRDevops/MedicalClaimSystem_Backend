package com.hcl.mediclaim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.mediclaim.dto.HospitalResponseDto;
import com.hcl.mediclaim.dto.ValidatePolicyResponseDto;
import com.hcl.mediclaim.exception.PolicyNotPresentException;
import com.hcl.mediclaim.service.HospitalService;
import com.hcl.mediclaim.util.MediClaimUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/vi")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@Slf4j
public class HospitalController {
	
	@Autowired
	HospitalService hospitalService;
	
	
	
}
	
