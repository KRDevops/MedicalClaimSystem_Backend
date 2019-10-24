package com.hcl.mediclaim.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApprovalDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long claimId;
	private String diagnosis;
	private LocalDate claimDate;
	private LocalDate admissionDate;
	private LocalDate dischargeDate;
	private String hospitalName;
	private String claimStatus;
	private Double claimAmount;
	private String documents;
	private Long policyNumber;
	private Integer deviationPercent;
	private String remarks;
	private Long userId;

}
