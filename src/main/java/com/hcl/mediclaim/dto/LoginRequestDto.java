package com.hcl.mediclaim.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String emailId;
	private String password;
}
