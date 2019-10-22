package com.hcl.mediclaim.service;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.hcl.mediclaim.repository.ClaimRepository;
import com.hcl.mediclaim.repository.HospitalRepository;
import com.hcl.mediclaim.repository.RoleRepository;
import com.hcl.mediclaim.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class ApprovalServiceTest {

	@InjectMocks
	ApprovalServiceImpl approvalServiceImpl;

	@Mock
	ClaimRepository claimRepository;
	@Mock
	HospitalRepository hospitalRepository;
	@Mock
	RoleRepository roleRepository;
	@Mock
	UserRepository userRepository;
	
	
}
