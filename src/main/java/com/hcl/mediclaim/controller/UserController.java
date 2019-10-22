package com.hcl.mediclaim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.mediclaim.dto.LoginRequestDto;
import com.hcl.mediclaim.dto.LoginResponseDto;
import com.hcl.mediclaim.exception.ApproverNotFoundException;
import com.hcl.mediclaim.exception.UserNotFoundException;
import com.hcl.mediclaim.service.UserService;
import com.hcl.mediclaim.util.MediClaimUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @since 2019-10-21 This class includes methods for login functionality for
 *        approver in mediclaim management system.
 */
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RequestMapping("api/v1")
@Slf4j
public class UserController {
	@Autowired
	UserService userService;

	/**
	 * 
	 * @param userLoginRequestDto
	 * @return
	 * 
	 *         Method used for login functionality into book management system.
	 * @throws ApproverNotFoundException
	 * @throws UserNotFoundException
	 */
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto userLoginRequestDto)
			throws UserNotFoundException, ApproverNotFoundException {
		log.info("login in user controller method started");
		LoginResponseDto userLoginResponseDto = userService.login(userLoginRequestDto);
		userLoginResponseDto.setMessage(MediClaimUtil.LOGIN_SUCCESS);
		userLoginResponseDto.setStatusCode(MediClaimUtil.GENERICSUCCESSCODE);
		log.info("login in user controller method ended");
		return new ResponseEntity<>(userLoginResponseDto, HttpStatus.OK);
	}

}
