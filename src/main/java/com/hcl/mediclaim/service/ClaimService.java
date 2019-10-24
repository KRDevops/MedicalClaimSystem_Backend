package com.hcl.mediclaim.service;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.web.multipart.MultipartFile;

import com.hcl.mediclaim.dto.ClaimResponseDto;
import com.hcl.mediclaim.exception.MediClaimException;

public interface ClaimService {
	
	public ClaimResponseDto create (MultipartFile documents,String claimRequestDto) throws IOException,MediClaimException,MessagingException;
}
