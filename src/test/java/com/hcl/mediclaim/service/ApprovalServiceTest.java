package com.hcl.mediclaim.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.hcl.mediclaim.dto.ApprovalDto;
import com.hcl.mediclaim.dto.ApprovalResponseDto;
import com.hcl.mediclaim.dto.ApproveRequestDto;
import com.hcl.mediclaim.dto.ResponseDto;
import com.hcl.mediclaim.entity.Claim;
import com.hcl.mediclaim.entity.Hospital;
import com.hcl.mediclaim.entity.Policy;
import com.hcl.mediclaim.entity.Role;
import com.hcl.mediclaim.entity.User;
import com.hcl.mediclaim.exception.ApproverNotFoundException;
import com.hcl.mediclaim.exception.MediClaimException;
import com.hcl.mediclaim.repository.ClaimRepository;
import com.hcl.mediclaim.repository.HospitalRepository;
import com.hcl.mediclaim.repository.PolicyRepository;
import com.hcl.mediclaim.repository.RoleRepository;
import com.hcl.mediclaim.repository.UserRepository;
import com.hcl.mediclaim.util.JavaMailUtil;

@RunWith(MockitoJUnitRunner.class)
public class ApprovalServiceTest {
	@Mock
	ClaimRepository claimRepository;
	@Mock
	HospitalRepository hospitalRepository;
	@Mock
	RoleRepository roleRepository;
	@Mock
	UserRepository userRepository;
	@Mock
	PolicyRepository policyRepository;
	@Mock
	JavaMailUtil javaMailUtil;
	@InjectMocks
	ApprovalServiceImpl approvalService;

	ApproveRequestDto approveRequestDto = new ApproveRequestDto();
	Policy policy = new Policy();
	ResponseDto responseDto = new ResponseDto();
	Claim claim = new Claim();
	User approver = new User();
	User user = new User();
	User seniorApprover = new User();
	Hospital hospital = new Hospital();
	Role role = new Role();
	List<User> seniorApproverList = new ArrayList<>();

	@Before
	public void setUp() {

		approveRequestDto.setApproverId(1L);
		approveRequestDto.setClaimId(10L);
		approveRequestDto.setRemarks("Approve");
		approveRequestDto.setStatus("APPROVE");

		approver.setUserId(1L);
		approver.setAadharNumber("343598982323");
		approver.setAddress("Marathahalli");
		approver.setCity("Bangalore");
		approver.setCountry("India");
		approver.setDateOfBirth(LocalDate.of(1991, 8, 07));
		approver.setEmailId("tsbalaji10@gmail.com");
		approver.setPancardNumber("ASOPT7334N");
		approver.setPassword("balaji123");
		approver.setPhoneNumber(9840651207L);
		approver.setPincode(600028L);
		approver.setUserName("Balaji");
		approver.setRoleId(new Role(2L, "APPROVER", "APPROVER"));

		seniorApprover.setUserId(2L);
		seniorApprover.setAadharNumber("313923456789");
		seniorApprover.setAddress("Madivala");
		seniorApprover.setCity("Bangalore");
		seniorApprover.setCountry("India");
		seniorApprover.setDateOfBirth(LocalDate.of(1993, 8, 9));
		seniorApprover.setEmailId("kpranaya96@gmail.com");
		seniorApprover.setPancardNumber("ASOPT1223N");
		seniorApprover.setPassword("pranya123");
		seniorApprover.setPhoneNumber(8884148999L);
		seniorApprover.setPincode(600027L);
		seniorApprover.setUserName("Pranaya");
		seniorApprover.setRoleId(new Role(3L, "SENIOR_APPROVER", "SENIOR_APPROVER"));

		user.setUserId(6L);
		user.setAadharNumber("313923456789");
		user.setAddress("Bommanahalli");
		user.setCity("Bangalore");
		user.setCountry("India");
		user.setDateOfBirth(LocalDate.of(1987, 4, 2));
		user.setEmailId("tanmay.pattanaik@gmail.com");
		user.setPancardNumber("ALSOR2334N");
		user.setPassword("tanmay@123");
		user.setPhoneNumber(8884148000L);
		user.setPincode(560100L);
		user.setUserName("Tanmay");
		user.setRoleId(new Role(1L, "USER", "USER"));

		policy.setPolicyNumber(11L);
		policy.setAvailableAmount(5000.00);
		hospital.setHospitalId(5L);
		hospital.setCountry("India");
		claim.setClaimId(10L);
		claim.setUserId(user);
		claim.setPolicyNumber(policy);
		claim.setHospitalId(hospital);
		claim.setDeviationPercentage(15);
		claim.setClaimAmount(2000.00);
		approver.setUserId(20L);
		role.setRoleId(2L);
		role.setRoleName("APPROVER");
		seniorApproverList.add(seniorApprover);
	}

	@Test
	public void testViewApprove() throws ApproverNotFoundException {
		ApprovalResponseDto dto = new ApprovalResponseDto();
		dto.setStatusCode(200);
		Pageable pageable = PageRequest.of(1, 1);
		User user = new User();
		user.setUserId(2l);
		Policy policy = new Policy();
		policy.setPolicyNumber(1l);
		Claim claim = new Claim();
		claim.setApproverId(user);
		claim.setUserId(user);
		Hospital hos = new Hospital();
		hos.setHospitalId(1l);
		hos.setHospitalName("kg");
		List<Hospital> hosp = new ArrayList<>();
		hosp.add(hos);
		claim.setHospitalId(hos);
		claim.setPolicyNumber(policy);
		claim.setClaimId(1l);
		claim.setSeniorApproverId(user);

		List<Claim> claims = new ArrayList<>();
		claims.add(claim);
		Mockito.when(claimRepository.findByApproverId(Mockito.any(), Mockito.any())).thenReturn(Optional.of(claims));
		Mockito.when(hospitalRepository.findAll()).thenReturn(hosp);
		List<ApprovalDto> response = approvalService.approve(2l, 0);
		assertEquals(response.size(), claims.size());
	}

	@Test
	public void testApprove() throws MediClaimException, MessagingException {
		Mockito.when(userRepository.findByUserId(approveRequestDto.getApproverId())).thenReturn(Optional.of(approver));
		Mockito.when(claimRepository.findByClaimId(approveRequestDto.getClaimId())).thenReturn(Optional.of(claim));
		/*
		 * Mockito.when(userRepository.findByRoleId( new Role(MediClaimUtil.THREE,
		 * MediClaimUtil.SENIOR_APPROVER_ROLE, MediClaimUtil.SENIOR_APPROVER_ROLE)))
		 * .thenReturn(Optional.of(seniorApproverList));
		 */
		ResponseDto responseUpdateDto = approvalService.approveOrReject(approveRequestDto);
		assertNotNull(responseUpdateDto);

	}

	@Test(expected = MediClaimException.class)
	public void negativeTestClaimAvailability() throws IOException, MediClaimException, MessagingException {
		Mockito.when(claimRepository.findByClaimId(Mockito.any())).thenReturn(Optional.empty());
		approvalService.approveOrReject(approveRequestDto);
	}

	@Test
	public void testPassIfDeviationPercentGreaterThan10() throws IOException, MediClaimException, MessagingException {
		Mockito.when(userRepository.findByUserId(Mockito.any())).thenReturn(Optional.of(approver));
		Mockito.when(claimRepository.findByClaimId(Mockito.any())).thenReturn(Optional.of(claim));
		responseDto = approvalService.approveOrReject(approveRequestDto);
		assertEquals("MOVE", responseDto.getMessage());
	}

	@Test
	public void testRejectClaim() throws IOException, MediClaimException, MessagingException {
		ApproveRequestDto approveRequestDto = new ApproveRequestDto();
		approveRequestDto.setApproverId(1L);
		approveRequestDto.setClaimId(10L);
		approveRequestDto.setRemarks("Reject");
		approveRequestDto.setStatus("REJECT");
		Mockito.when(userRepository.findByUserId(approveRequestDto.getApproverId())).thenReturn(Optional.of(approver));
//		Mockito.when(claimRepository.findByClaimId(approveRequestDto.getClaimId())).thenReturn(Optional.of(claim));
		/*
		 * Mockito.when(userRepository.findByRoleId( new Role(MediClaimUtil.THREE,
		 * MediClaimUtil.SENIOR_APPROVER_ROLE, MediClaimUtil.SENIOR_APPROVER_ROLE)))
		 * .thenReturn(Optional.of(seniorApproverList));
		 */
		Mockito.when(claimRepository.findByClaimId(Mockito.any())).thenReturn(Optional.of(claim));
		responseDto = approvalService.approveOrReject(approveRequestDto);
		assertEquals("REJECTED", responseDto.getMessage());
	}

	@Test(expected = MediClaimException.class)
	public void testPassClaimException() throws IOException, MediClaimException, MessagingException {
		ApproveRequestDto approveRequestDto = new ApproveRequestDto();
		approveRequestDto.setApproverId(1L);
		approveRequestDto.setClaimId(10L);
		approveRequestDto.setRemarks("Pass");
		approveRequestDto.setStatus("PASS");
		Mockito.when(userRepository.findByUserId(approveRequestDto.getApproverId())).thenReturn(Optional.of(approver));
//		Mockito.when(claimRepository.findByClaimId(approveRequestDto.getClaimId())).thenReturn(Optional.of(claim));
		/*
		 * Mockito.when(userRepository.findByRoleId( new Role(MediClaimUtil.THREE,
		 * MediClaimUtil.SENIOR_APPROVER_ROLE, MediClaimUtil.SENIOR_APPROVER_ROLE)))
		 * .thenReturn(Optional.of(seniorApproverList));
		 */
		Mockito.when(claimRepository.findByClaimId(Mockito.any())).thenReturn(Optional.of(claim));
		approvalService.approveOrReject(approveRequestDto);
	}

	@Test
	public void testSeniorApproverApproveClaim() throws IOException, MediClaimException, MessagingException {
		ApproveRequestDto approveRequestDto = new ApproveRequestDto();
		approveRequestDto.setApproverId(2L);
		approveRequestDto.setClaimId(10L);
		approveRequestDto.setRemarks("Approve");
		approveRequestDto.setStatus("APPROVE");
		Claim claim = new Claim();
		claim.setClaimId(10L);
		claim.setUserId(user);
		claim.setPolicyNumber(policy);
		claim.setHospitalId(hospital);
		claim.setDeviationPercentage(15);
		claim.setClaimAmount(2000.00);
		Mockito.when(userRepository.findByUserId(approveRequestDto.getApproverId()))
				.thenReturn(Optional.of(seniorApprover));
//		Mockito.when(claimRepository.findByClaimId(approveRequestDto.getClaimId())).thenReturn(Optional.of(claim));
		/*
		 * Mockito.when(userRepository.findByRoleId( new Role(MediClaimUtil.THREE,
		 * MediClaimUtil.SENIOR_APPROVER_ROLE, MediClaimUtil.SENIOR_APPROVER_ROLE)))
		 * .thenReturn(Optional.of(seniorApproverList));
		 */
		Mockito.when(claimRepository.findByClaimId(Mockito.any())).thenReturn(Optional.of(claim));
		Mockito.when(policyRepository.save(Mockito.any())).thenReturn(policy);
		ResponseDto responseDto = approvalService.approveOrReject(approveRequestDto);
		assertEquals(new Integer(200), responseDto.getStatusCode());
	}

	@Test
	public void testSeniorApproverRejectClaim() throws IOException, MediClaimException, MessagingException {
		ApproveRequestDto approveRequestDto = new ApproveRequestDto();
		approveRequestDto.setApproverId(2L);
		approveRequestDto.setClaimId(10L);
		approveRequestDto.setRemarks("Reject");
		approveRequestDto.setStatus("REJECT");
		Claim claim = new Claim();
		claim.setClaimId(10L);
		claim.setUserId(user);
		claim.setPolicyNumber(policy);
		claim.setHospitalId(hospital);
		claim.setDeviationPercentage(15);
		claim.setClaimAmount(2000.00);
		Mockito.when(userRepository.findByUserId(approveRequestDto.getApproverId()))
				.thenReturn(Optional.of(seniorApprover));
//		Mockito.when(claimRepository.findByClaimId(approveRequestDto.getClaimId())).thenReturn(Optional.of(claim));
		/*
		 * Mockito.when(userRepository.findByRoleId( new Role(MediClaimUtil.THREE,
		 * MediClaimUtil.SENIOR_APPROVER_ROLE, MediClaimUtil.SENIOR_APPROVER_ROLE)))
		 * .thenReturn(Optional.of(seniorApproverList));
		 */
		Mockito.when(claimRepository.findByClaimId(Mockito.any())).thenReturn(Optional.of(claim));
		ResponseDto responseDto = approvalService.approveOrReject(approveRequestDto);
		assertEquals(new Integer(200), responseDto.getStatusCode());
	}
}