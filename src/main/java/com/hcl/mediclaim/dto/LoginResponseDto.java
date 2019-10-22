package com.hcl.mediclaim.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginResponseDto {
	private Long userId;
	private String message;
	private int statusCode;
	private Long roleId;
}
