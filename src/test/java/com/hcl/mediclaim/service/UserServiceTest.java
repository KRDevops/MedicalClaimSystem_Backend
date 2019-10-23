package com.hcl.mediclaim.service;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.hcl.mediclaim.dto.LoginRequestDto;
import com.hcl.mediclaim.dto.LoginResponseDto;
import com.hcl.mediclaim.entity.Role;
import com.hcl.mediclaim.entity.User;
import com.hcl.mediclaim.exception.ApproverNotFoundException;
import com.hcl.mediclaim.exception.UserNotFoundException;
import com.hcl.mediclaim.repository.RoleRepository;
import com.hcl.mediclaim.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock
	UserRepository userRepository;

	@Mock
	RoleRepository roleRepository;

	@InjectMocks
	UserServiceImpl userServiceImpl;

	LoginRequestDto loginRequestDto;

	LoginResponseDto loginResponseDto;

	User user = new User();

	Role role = new Role();

	LoginRequestDto loginRequest = new LoginRequestDto();

	LoginResponseDto userLoginResponseDto = new LoginResponseDto();

	Role approverRole = new Role();
	User approverUser = new User();

	Role seniorApproverRole = new Role();

	@Before
	public void setup() {
		loginRequest.setEmailId("k@gmail.com");
		loginRequest.setPassword("ju89");

		userLoginResponseDto.setMessage("success");
		userLoginResponseDto.setStatusCode(201);
		userLoginResponseDto.setUserId(1L);

		approverRole.setRoleId(2L);
		approverRole.setRoleName("APPROVER");

		approverUser.setUserId(5L);
		approverUser.setRoleId(approverRole);

		seniorApproverRole.setRoleId(3L);
		seniorApproverRole.setRoleName("SENIOR_APPROVER");

		role.setRoleId(6L);
		role.setRoleName("USER");
		role.setRoleDescription("User Role");

		user.setEmailId("tsb@gmail.com");
		user.setPassword("balaji123");
		user.setUserId(1L);
		user.setRoleId(role);

	}

	@Test
	public void positiveTestApproverLogin() throws UserNotFoundException, ApproverNotFoundException {

		Mockito.when(userRepository.findByEmailIdAndPassword(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(Optional.of(approverUser));
		Mockito.when(roleRepository.findByRoleId(Mockito.any())).thenReturn(Optional.of(approverRole));
		LoginResponseDto userLoginResponseDto = userServiceImpl.login(loginRequest);
		Assert.assertEquals(Long.valueOf(5L), userLoginResponseDto.getUserId());
	}

	@Test
	public void positiveTestSeniorApproverLogin() throws UserNotFoundException, ApproverNotFoundException {

		Mockito.when(userRepository.findByEmailIdAndPassword(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(Optional.of(approverUser));
		Mockito.when(roleRepository.findByRoleId(Mockito.any())).thenReturn(Optional.of(seniorApproverRole));
		LoginResponseDto userLoginResponseDto = userServiceImpl.login(loginRequest);
		Assert.assertEquals(Long.valueOf(5L), userLoginResponseDto.getUserId());
	}

	@Test(expected = UserNotFoundException.class)
	public void negativeTestLogin() throws UserNotFoundException, ApproverNotFoundException {

		Mockito.when(userRepository.findByEmailIdAndPassword(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(Optional.empty());
		LoginResponseDto userLoginResponseDto = userServiceImpl.login(loginRequest);
		Assert.assertEquals(Long.valueOf(1L), userLoginResponseDto.getUserId());

	}

	@Test(expected = ApproverNotFoundException.class)
	public void negativeTestApproverLogin() throws UserNotFoundException, ApproverNotFoundException {

		Mockito.when(userRepository.findByEmailIdAndPassword(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(Optional.of(user));
		Mockito.when(roleRepository.findByRoleId(Mockito.any())).thenReturn(Optional.of(role));
		LoginResponseDto userLoginResponseDto = userServiceImpl.login(loginRequest);
		Assert.assertEquals(Long.valueOf(1L), userLoginResponseDto.getUserId());

	}

}
