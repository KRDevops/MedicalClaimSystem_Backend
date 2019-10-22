package com.hcl.mediclaim.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClaimResponseDto {

	private Long claimId;
	private String message;
	private Integer statusCode;
}
