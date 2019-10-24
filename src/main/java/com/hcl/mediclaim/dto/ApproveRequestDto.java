package com.hcl.mediclaim.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApproveRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long claimId;
	private Long approverId;
	private String status;
	private String remarks;
}
