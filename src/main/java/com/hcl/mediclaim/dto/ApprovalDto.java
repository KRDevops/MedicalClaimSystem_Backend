package com.hcl.mediclaim.dto;




import java.time.LocalDate;

import javax.persistence.Lob;

import org.springframework.web.multipart.MultipartFile;

import com.hcl.mediclaim.entity.Policy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class ApprovalDto {
	 private Long claimId;
	private String diagnosis;
	private LocalDate claimDate;
	private LocalDate admissionDate;
	private LocalDate dischargeDate;
	private String hospitalName;
	private String claimStatus;
	@Lob
	private String  documents;
	private  Double  claimAmount;
	private Long policyNumber;
	private Integer deviationPercent;
	private String remarks;
	private Long userId;
	private ApprovalResponseDto data;
}
