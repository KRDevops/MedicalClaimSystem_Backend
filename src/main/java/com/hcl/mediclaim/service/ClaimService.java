package com.hcl.mediclaim.service;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import org.springframework.web.multipart.MultipartFile;

import com.hcl.mediclaim.dto.ClaimResponseDto;
import com.hcl.mediclaim.exception.MediClaimException;

public interface ClaimService {
	public ClaimResponseDto create (MultipartFile documents,String claimRequestDto) throws IOException,SendFailedException,MediClaimException,MessagingException;
	/*
	 * public ClaimResponseDto create (MultipartFile documents,String diagnosis,
	 * LocalDate admissionDate, LocalDate dischargeDate, Long hospitalId, Long
	 * policyNumber, Ailment natureOfAilment,Long userId, Double claimAmount) throws
	 * IOException,SendFailedException;
	 */
}
