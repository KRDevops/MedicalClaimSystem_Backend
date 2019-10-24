package com.hcl.mediclaim.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.mediclaim.dto.LoginRequestDto;
import com.hcl.mediclaim.dto.LoginResponseDto;
import com.hcl.mediclaim.entity.Role;
import com.hcl.mediclaim.entity.User;
import com.hcl.mediclaim.exception.ApproverNotFoundException;
import com.hcl.mediclaim.exception.UserNotFoundException;
import com.hcl.mediclaim.repository.RoleRepository;
import com.hcl.mediclaim.repository.UserRepository;
import com.hcl.mediclaim.util.MediClaimUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Balaji
 * @since 2019-10-21 This class includes methods for login functionality for
 *        approver in mediclaim management system.
 */

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	/**
	 * @author Balaji
	 * @return This method is used to login to the book management system and
	 *         returns success message.
	 * 
	 * @throws UserNotFoundException Custom Exceptions
	 * @throws ApproverNotFoundException Custom Exceptions
	 * 
	 * 
	 */
	@Override
	public LoginResponseDto login(LoginRequestDto userLoginRequestDto)
			throws UserNotFoundException, ApproverNotFoundException {
		log.info("login method in UserService started");
		
		Optional<User> user = userRepository.findByEmailIdAndPassword(userLoginRequestDto.getEmailId(),
				userLoginRequestDto.getPassword());
		
		if (!user.isPresent()) {
			throw new UserNotFoundException(MediClaimUtil.USER_NOT_FOUND);
		}

		Optional<Role> role = roleRepository.findByRoleId(user.get().getRoleId().getRoleId());
		
		LoginResponseDto userLoginResponseDto = new LoginResponseDto();
		
		if (role.isPresent()) {
			if (role.get().getRoleName().equalsIgnoreCase(MediClaimUtil.APPROVER_ROLE)
					|| role.get().getRoleName().equalsIgnoreCase(MediClaimUtil.SENIOR_APPROVER_ROLE)) {
				userLoginResponseDto.setUserId(user.get().getUserId());
				userLoginResponseDto.setRoleId(role.get().getRoleId());
			} else {
				throw new ApproverNotFoundException(MediClaimUtil.INVALID_USER);
			}
		}
		log.info("login method in UserService ended");
		return userLoginResponseDto;
	}
}
