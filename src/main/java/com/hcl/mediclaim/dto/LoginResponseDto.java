package com.hcl.mediclaim.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginResponseDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long userId;
	private String message;
	private int statusCode;
	private Long roleId;
}
