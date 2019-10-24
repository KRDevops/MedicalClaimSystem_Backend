package com.hcl.mediclaim.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApprovalResponseDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<ApprovalDto> claim;
	private Integer statusCode;
	private String message;
	private Integer count;
}
