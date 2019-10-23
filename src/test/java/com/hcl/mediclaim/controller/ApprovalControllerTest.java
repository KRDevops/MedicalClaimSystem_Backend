package com.hcl.mediclaim.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.hcl.mediclaim.dto.ApprovalDto;
import com.hcl.mediclaim.dto.ApprovalResponseDto;
import com.hcl.mediclaim.dto.ApproveRequestDto;
import com.hcl.mediclaim.dto.ResponseDto;
import com.hcl.mediclaim.exception.ApproverNotFoundException;
import com.hcl.mediclaim.exception.MediClaimException;
import com.hcl.mediclaim.service.ApprovalService;
import com.hcl.mediclaim.util.MediClaimUtil;

@RunWith(MockitoJUnitRunner.class)
public class ApprovalControllerTest {
	@Mock
	ApprovalService approvalService;

	@InjectMocks
	ApprovalController approvalController;

	ApproveRequestDto approveRequestDto = new ApproveRequestDto();
	ResponseDto responseDto = new ResponseDto();
	ResponseDto negativeResponseDto = new ResponseDto();
	ApprovalDto approvalDto = new ApprovalDto();
	List<ApprovalDto> approvalDtoList = new ArrayList<>();

	@Before
	public void setup() {

		approveRequestDto.setApproverId(1L);
		approveRequestDto.setClaimId(10L);
		approveRequestDto.setRemarks("Approve");
		approveRequestDto.setStatus("APPROVE");

		responseDto.setMessage(MediClaimUtil.GENERICSUCCESSMESSAGE);
		responseDto.setStatusCode(MediClaimUtil.GENERICSUCCESSCODE);

		negativeResponseDto.setMessage("Failed");
		negativeResponseDto.setStatusCode(MediClaimUtil.GENERICFAILURECODE);

		approvalDto.setClaimId(6L);
		approvalDto.setAdmissionDate(LocalDate.of(2019, 7, 1));
		approvalDto.setClaimAmount(20010.00);
		approvalDto.setClaimDate(LocalDate.of(2019, 10, 22));
		approvalDto.setClaimStatus("APPROVE");
		approvalDto.setDeviationPercent(0);
		approvalDto.setDiagnosis("Dengue");
		approvalDto.setDischargeDate(LocalDate.of(2019, 10, 5));
		approvalDto.setUserId(1L);
		approvalDto.setHospitalName("Global");
		approvalDto.setDocuments("KondaReddy17.pdf");

		approvalDtoList.add(approvalDto);
	}

	@Test
	public void positiveTestReview() throws MediClaimException, MessagingException {
		Mockito.when(approvalService.approveOrReject(Mockito.any())).thenReturn(responseDto);
		ResponseDto actual = approvalController.review(approveRequestDto);
		assertEquals(MediClaimUtil.GENERICSUCCESSCODE, actual.getStatusCode());
	}

	@Test
	public void negativeTestReview() throws MediClaimException, MessagingException {
		Mockito.when(approvalService.approveOrReject(Mockito.any())).thenReturn(negativeResponseDto);
		ResponseDto actual = approvalController.review(approveRequestDto);
		assertEquals(MediClaimUtil.GENERICFAILURECODE, actual.getStatusCode());
	}

	@Test
	public void positiveView() throws ApproverNotFoundException {
		Mockito.when(approvalService.viewApprovals(Mockito.any(), Mockito.any())).thenReturn(approvalDtoList);
		ResponseEntity<ApprovalResponseDto> actual = approvalController.view(1L, 5);
		assertEquals(MediClaimUtil.GENERICSUCCESSCODE, actual.getStatusCodeValue());

	}
}
