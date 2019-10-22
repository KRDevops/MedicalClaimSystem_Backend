package com.hcl.mediclaim.service;

import com.hcl.mediclaim.dto.LoginRequestDto;
import com.hcl.mediclaim.dto.LoginResponseDto;
import com.hcl.mediclaim.exception.ApproverNotFoundException;
import com.hcl.mediclaim.exception.UserNotFoundException;

public interface UserService {
	LoginResponseDto login(LoginRequestDto userLoginRequestDto) throws UserNotFoundException, ApproverNotFoundException;
}
