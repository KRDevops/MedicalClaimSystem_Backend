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

/**
 * @author Afrin
 * @since 2019-10-22
 *
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@Slf4j
public class ClaimController {

	@Autowired
	ClaimService claimService;

	@PostMapping(consumes = MediaType.ALL_VALUE, path = "claims/create")
	public ClaimResponseDto create(@NotNull @RequestPart("file") MultipartFile documents,
			@NotNull @RequestPart("requests") String claimRequestDto)
			throws SendFailedException, IOException, MediClaimException,MessagingException {
		log.info("Claim Controller Create Method Started");
		ClaimResponseDto claimResponseDto = claimService.create(documents, claimRequestDto);
		if (claimResponseDto.getClaimId() != null) {
			claimResponseDto.setMessage("Success");
			claimResponseDto.setStatusCode(200);
		} else {
			claimResponseDto.setMessage("Failed");
			claimResponseDto.setStatusCode(404);
		}
		log.info("Claim Controller Create Method Ended");
		return claimResponseDto;
	}
}
