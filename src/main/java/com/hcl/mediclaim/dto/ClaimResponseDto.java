package com.hcl.mediclaim.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClaimResponseDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long claimId;
	private String message;
	private Integer statusCode;
}
