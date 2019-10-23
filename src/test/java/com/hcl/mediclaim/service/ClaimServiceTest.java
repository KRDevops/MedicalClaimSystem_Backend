package com.hcl.mediclaim.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import com.hcl.mediclaim.dto.ClaimResponseDto;
import com.hcl.mediclaim.entity.Claim;
import com.hcl.mediclaim.entity.Hospital;
import com.hcl.mediclaim.entity.Policy;
import com.hcl.mediclaim.entity.Role;
import com.hcl.mediclaim.entity.User;
import com.hcl.mediclaim.exception.MediClaimException;
import com.hcl.mediclaim.repository.ClaimRepository;
import com.hcl.mediclaim.repository.HospitalRepository;
import com.hcl.mediclaim.repository.PolicyRepository;
import com.hcl.mediclaim.repository.RoleRepository;
import com.hcl.mediclaim.repository.UserRepository;
import com.hcl.mediclaim.util.JavaMailUtil;

@RunWith(MockitoJUnitRunner.class)
public class ClaimServiceTest {

	String claimRequestDto = null;

	@Mock
	ClaimRepository claimRepository;

	@Mock
	RoleRepository roleRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	PolicyRepository policyRepository;

	@Mock
	HospitalRepository hospitalRepository;

	@Mock
	JavaMailUtil javaMailUtil;

	@InjectMocks
	ClaimServiceImpl claimServiceImpl;

	User user = new User();
	Policy policy = new Policy();
	Hospital hospital = new Hospital();
	User approver = new User();
	Claim claim = new Claim();
	Role role = new Role();
	ClaimResponseDto claimResponseDto = new ClaimResponseDto();
	List<User> approverList = new ArrayList<>();

	@Before
	public void setUp() {

		claimRequestDto = "{\r\n" + "  \"admissionDate\": \"2019-07-01\",\r\n" + "  \"diagnosis\": \"Malaria\",\r\n"
				+ "  \"dischargeDate\": \"2019-10-05\",\r\n" + "  \"hospitalId\": 1,\r\n"
				+ "  \"natureOfAilment\": \"MINOR\",\r\n" + "  \"policyNumber\": 1,\r\n" + "  \"userId\": 1,\r\n"
				+ "  \"claimAmount\":5500\r\n" + "}";
		user.setUserId(1L);
		policy.setPolicyNumber(11L);
		policy.setAvailableAmount(5000.00);
		hospital.setHospitalId(5L);
		hospital.setCountry("India");
		claim.setClaimId(10L);
		claim.setUserId(user);
		claim.setPolicyNumber(policy);
		claim.setHospitalId(hospital);
		approver.setUserId(20L);
		role.setRoleId(2L);
		role.setRoleName("APPROVER");
		approverList.add(approver);
	}

	@Test
	public void positiveTestCreate() throws IOException, MediClaimException, MessagingException {

		Resource resource = new ClassPathResource("afrin11.pdf");
		File file = resource.getFile();
		MockMultipartFile multipartFile = new MockMultipartFile("afrin11", "afrin11.pdf", "application/pdf",
				new FileInputStream(file));
		Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
		Mockito.when(policyRepository.findById(Mockito.any())).thenReturn(Optional.of(policy));
		Mockito.when(hospitalRepository.findById(Mockito.any())).thenReturn(Optional.of(hospital));
		Mockito.when(claimRepository.save(Mockito.any())).thenReturn(claim);
		Mockito.when(roleRepository.findByRoleName(Mockito.any())).thenReturn(Optional.of(role));
		Mockito.when(userRepository.findByRoleId(Mockito.any())).thenReturn(Optional.of(approverList));
		Mockito.when(claimRepository.findTopByOrderByClaimIdDesc()).thenReturn(claim);
		claimResponseDto = claimServiceImpl.create(multipartFile, claimRequestDto);
		Assert.assertEquals(Long.valueOf(10), claimResponseDto.getClaimId());
	}

	@Test(expected = MediClaimException.class)
	public void negativeTestUser() throws IOException, MediClaimException, MessagingException {

		Resource resource = new ClassPathResource("afrin11.pdf");
		File file = resource.getFile();
		MockMultipartFile multipartFile = new MockMultipartFile("afrin11", "afrin11.pdf", "application/pdf",
				new FileInputStream(file));
		Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		claimResponseDto = claimServiceImpl.create(multipartFile, claimRequestDto);
	}
	
	@Test(expected = MediClaimException.class)
	public void negativeTestFileFormat() throws IOException, MediClaimException, MessagingException {

		Resource resource = new ClassPathResource("stock.csv");
		File file = resource.getFile();
		MockMultipartFile multipartFile = new MockMultipartFile("stock","stock.xlsx","application/pdf", new FileInputStream(file));
		Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		claimResponseDto = claimServiceImpl.create(multipartFile, claimRequestDto);
	}

	@Test(expected = MediClaimException.class)
	public void negativeTestPolicy() throws IOException, MediClaimException, MessagingException {

		Resource resource = new ClassPathResource("afrin11.pdf");
		File file = resource.getFile();
		MockMultipartFile multipartFile = new MockMultipartFile("afrin11", "afrin11.pdf", "application/pdf",
				new FileInputStream(file));
		Mockito.when(policyRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		claimResponseDto = claimServiceImpl.create(multipartFile, claimRequestDto);
	}

	@Test(expected = MediClaimException.class)
	public void negativeTestHospital() throws IOException, MediClaimException, MessagingException {

		Resource resource = new ClassPathResource("afrin11.pdf");
		File file = resource.getFile();
		MockMultipartFile multipartFile = new MockMultipartFile("afrin11", "afrin11.pdf", "application/pdf",
				new FileInputStream(file));
		Mockito.when(hospitalRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		claimResponseDto = claimServiceImpl.create(multipartFile, claimRequestDto);
	}
}
