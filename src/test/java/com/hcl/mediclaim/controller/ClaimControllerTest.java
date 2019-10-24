package com.hcl.mediclaim.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.mail.MessagingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hcl.mediclaim.dto.ClaimResponseDto;
import com.hcl.mediclaim.exception.MediClaimException;
import com.hcl.mediclaim.service.ClaimService;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class ClaimControllerTest {

	@Mock
	ClaimService claimService;

	@InjectMocks
	ClaimController claimcontroller;

	String claimRequestDto = null;
	MockMultipartFile multipartFile;
	ClaimResponseDto claimResponseDto = new ClaimResponseDto();
	ClaimResponseDto negClaimResponseDto = new ClaimResponseDto();

	@Before
	public void setUp() throws IOException {
		Resource resource = new ClassPathResource("afrin11.pdf");
		File file = resource.getFile();
		multipartFile = new MockMultipartFile("file", new FileInputStream(file));
		claimRequestDto = "{\r\n" + "  \"admissionDate\": \"2019-07-01\",\r\n" + "  \"diagnosis\": \"Malaria\",\r\n"
				+ "  \"dischargeDate\": \"2019-10-05\",\r\n" + "  \"hospitalId\": 1,\r\n"
				+ "  \"natureOfAilment\": \"MINOR\",\r\n" + "  \"policyNumber\": 1,\r\n" + "  \"userId\": 1,\r\n"
				+ "  \"claimAmount\":5500\r\n" + "}";
		claimResponseDto.setClaimId(1L);
		claimResponseDto.setMessage("Success");
		claimResponseDto.setStatusCode(200);
		negClaimResponseDto.setMessage("Failed");
		negClaimResponseDto.setStatusCode(404);
	}

	@Test
	public void positiveTestCreate() throws IOException, MediClaimException, MessagingException {
		Mockito.when(claimService.create(Mockito.any(), Mockito.any())).thenReturn(claimResponseDto);
		ClaimResponseDto actual = claimcontroller.create(multipartFile, claimRequestDto);
		Assert.assertEquals("Search is Successful", actual.getMessage());
	}

	@Test
	public void negativeTestCreate() throws IOException, MediClaimException, MessagingException {
		Mockito.when(claimService.create(Mockito.any(), Mockito.any())).thenReturn(negClaimResponseDto);
		ClaimResponseDto actual = claimcontroller.create(multipartFile, claimRequestDto);
		Assert.assertEquals("FAILED", actual.getMessage());
	}

}
