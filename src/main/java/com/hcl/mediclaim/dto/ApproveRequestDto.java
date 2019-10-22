package com.hcl.mediclaim.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApproveRequestDto {
	private Long claimId;
	private Long approverId;
	private String status;
	private String remarks;
}
