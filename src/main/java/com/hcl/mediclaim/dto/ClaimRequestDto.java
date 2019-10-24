package com.hcl.mediclaim.dto;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClaimRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String diagnosis;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate admissionDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dischargeDate;
	private Long hospitalId;
	private Long policyNumber;
	private String natureOfAilment;
	private Long userId;
	private Double claimAmount;
}
