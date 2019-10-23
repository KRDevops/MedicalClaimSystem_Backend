package com.hcl.mediclaim.util;

public class MediClaimUtil {

	private MediClaimUtil() {

	}

	public static final String SUCCESS = "Search is Successful";
	public static final String LOGIN_SUCCESS = "Logged-In Successfully";
	public static final String FILE_EMPTY_EXCEPTION = "Uploaded file is empty";
	public static final String EMAIL_EXCEPTION = "Email not Valid";
	public static final String INVALID_MOBILE_NUMBER_EXCEPTION = "Please enter valid mobile number";
	public static final String USER_NOT_FOUND = "User not found";
	public static final String POLICY_NOT_FOUND = "Policy not found";
	public static final String USER_ALREADY_EXISTS = "User already registered with this email id";
	public static final Integer GENERICSUCCESSCODE = 200;
	public static final String GENERICSUCCESSMESSAGE = "Success";
	public static final Integer GENERICFAILURECODE = 404;
	public static final String APPROVER_ROLE = "APPROVER";
	public static final String USER_ROLE = "USER";
	public static final String SENIOR_APPROVER_ROLE = "SENIOR_APPROVER";
	public static final String APPROVER_NOT_FOUND = "Invalid User";
	public static final String ACTIVE = "ACTIVE";
	public static final String INACTIVE = "INACTIVE";
	public static final String APPROVE_SUCCESS = "The claim amount has been approved successfully";
	public static final String APPROVED = "APPROVED";
	public static final String REJECTED = "REJECTED";
	public static final String PENDING = "PASS";
	public static final String SUBMITTED = "SUBMITTED";
	public static final String USER_APPROVED_MESSAGE = "Your claim is approved!! Payment is in progress.";
	public static final String USER_REJECTED_MESSAGE = "Your claim is rejected!! Please provide more details for approval";
	public static final Long THREE = 3L;
	public static final Long TWO = 2L;
	public static final String APPROVE = "APPROVE";
	public static final String REJECT = "REJECT";
	public static final Long TEN = 10L;
	public static final String PASS = "PASS";
	public static final String PASSED = "PASSED";
	public static final String AVAILABLE_LOW_EXCEPTION = "Available balance is low";

	public static final String HOSPITAL_NETWORK = "Hospital Found Outside Network";
	public static final String ATTACHMENT_PATH = "src/main/resources";
	public static final String FILE_EXTENSION = ".pdf";
	public static final String SUBMIT_USER_MESSAGE = "Hi ,\n Your Claim Request Has Been Submitted Successfully \n ";
	public static final String SUBMIT_APPROVER_MESSAGE = "Hi,\n A Mediclaim Request is waiting for your approval \n";
	public static final String SUBMIT_USER_SUBJECT = "MEDICLAIM STATUS";
	public static final String SUBMIT_APPROVER_SUBJECT = "MEDICLAIM APPROVAL REQUEST";
	public static final String COUNTRY = "India";
	public static final String SENIOR_APPROVER_NOT_PRESENT = "Senior Approver is not present";
	public static final String MOVE = "MOVE";
	public static final String APRROVEORREJECTBYSENIOR = "Approve/Reject by Senior Approver is done";
	public static final String CLAIM_FOR_REJECTION_NOT_AVAILABLE = "No claim is present for rejection";
	public static final String CLAIM_FOR_APPROVAL_NOT_AVAILABLE = "No claim is present for Approval";
	public static final String CLAIM_NOT_AVAILABLE = "No Claim is available";
}
