package com.hcl.mediclaim.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hcl.mediclaim.dto.LoginRequestDto;
import com.hcl.mediclaim.dto.LoginResponseDto;
import com.hcl.mediclaim.exception.ApproverNotFoundException;
import com.hcl.mediclaim.exception.UserNotFoundException;
import com.hcl.mediclaim.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	@InjectMocks
	UserController userController;

	@Mock
	UserService userService;

	LoginRequestDto loginRequestDto;

	LoginRequestDto loginRequest = new LoginRequestDto();

	LoginResponseDto loginResponseDto;

	LoginResponseDto userLoginResponse = new LoginResponseDto();

	@Before
	public void setup() {

		userLoginResponse.setMessage("SUCCESS");
		userLoginResponse.setStatusCode(201);
		userLoginResponse.setUserId(1L);

		loginRequest.setEmailId("k@gmail.com");
		loginRequest.setPassword("yu78");

	}

	@Test
	public void testLogin() throws UserNotFoundException, ApproverNotFoundException {
		Mockito.when(userService.login(Mockito.any())).thenReturn(userLoginResponse);
		ResponseEntity<LoginResponseDto> actual = userController.login(loginRequestDto);
		ResponseEntity<LoginResponseDto> expected = new ResponseEntity<LoginResponseDto>(userLoginResponse,
				HttpStatus.OK);
		assertEquals(expected.getStatusCode().value(), actual.getStatusCodeValue());

	}

}
