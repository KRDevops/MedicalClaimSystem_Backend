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

	RoleRepository roleRepository;

	@InjectMocks

	UserServiceImpl userServiceImpl;

	LoginRequestDto loginRequestDto;

	LoginResponseDto loginResponseDto;

	User user = new User();

	Role role = new Role();

	LoginRequestDto loginRequest = new LoginRequestDto();

	LoginResponseDto userLoginResponseDto = new LoginResponseDto();

	@Before
	public void setup() {
		loginRequest.setEmailId("k@gmail.com");
		loginRequest.setPassword("ju89");

		userLoginResponseDto.setMessage("success");
		userLoginResponseDto.setStatusCode(201);
		userLoginResponseDto.setUserId(1L);

	}

	@Test
	public void testLogin() throws UserNotFoundException, ApproverNotFoundException {
		user.setEmailId("tsb@gmail.com");
		user.setPassword("balaji123");
		user.setUserId(1L);
		Optional<User> optUser = Optional.of(user);
		role.setRoleId(2L);
		role.setRoleName("APPROVER");
		role.setRoleDescription("Approver role");
		Optional<Role> optRole = Optional.of(role);
		Mockito.when(userRepository.findByEmailIdAndPassword(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(optUser);
		Mockito.when(roleRepository.findById(Mockito.any())).thenReturn(optRole);
		LoginResponseDto userLoginResponseDto = userServiceImpl.login(loginRequest);
		Assert.assertEquals(Long.valueOf(1L), userLoginResponseDto.getUserId());

	}

}
