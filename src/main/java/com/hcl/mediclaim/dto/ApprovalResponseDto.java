package com.hcl.mediclaim.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApprovalResponseDto {
	private List<ApprovalDto> claim;
	private Integer count;
private Integer statusCode;
private String message;
}
