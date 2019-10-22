package com.hcl.mediclaim.controller;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hcl.mediclaim.dto.ClaimResponseDto;
import com.hcl.mediclaim.exception.MediClaimException;
import com.hcl.mediclaim.service.ClaimService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@Slf4j
public class ClaimController {

	@Autowired
	ClaimService claimService;

	@PostMapping(consumes = MediaType.ALL_VALUE, path = "claims/creates")
	public ClaimResponseDto creates(@NotNull @RequestPart("file") MultipartFile documents,
			@NotNull @RequestPart("requests") String claimRequestDto)
			throws SendFailedException, IOException, MediClaimException,MessagingException {
		ClaimResponseDto claimResponseDto = claimService.create(documents, claimRequestDto);
		if (claimResponseDto.getClaimId() != null) {
			claimResponseDto.setMessage("Success");
			claimResponseDto.setStatusCode(200);
		} else {
			claimResponseDto.setMessage("Failed");
			claimResponseDto.setStatusCode(404);
		}
		return claimResponseDto;
	}

	/*
	 * @PostMapping(consumes = MediaType.ALL_VALUE, path = "claims/create") public
	 * ClaimResponseDto create(@RequestParam("file") MultipartFile
	 * documents, @RequestParam String diagnosis,
	 * 
	 * @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate
	 * admissionDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")
	 * LocalDate dischargeDate, @RequestParam Long hospitalId,
	 * 
	 * @RequestParam Long policyNumber, @RequestParam Ailment
	 * natureOfAilment, @RequestParam Long userId, @RequestParam Double claimAmount)
	 * throws IOException, SendFailedException { return
	 * claimService.create(documents, diagnosis, admissionDate, dischargeDate,
	 * hospitalId, policyNumber, natureOfAilment, userId, claimAmount) ;
	 * 
	 * }
	 */
}
