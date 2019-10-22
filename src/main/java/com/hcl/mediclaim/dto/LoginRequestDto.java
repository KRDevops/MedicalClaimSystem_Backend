package com.hcl.mediclaim.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginRequestDto {
	private String emailId;
	private String password;
}
