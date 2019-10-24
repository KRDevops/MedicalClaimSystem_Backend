package com.hcl.mediclaim.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ValidatePolicyResponseDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String message;
	private int statusCode;
	private double availableAmount;
	private String policyName;
	private double entitledAmount;
	private String status;
	private LocalDate expiryDate;
	private Long userId;
	
	}
