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
	public static final String APPROVER_NOT_FOUND = "User trying to login is not an approver or senior approver";
	public static final String ACTIVE = "ACTIVE";
	public static final String INACTIVE = "INACTIVE";
	public static final String HOSPITAL_NETWORK = "Hospital Found Outside Network";
	public static final String ATTACHMENT_PATH = "src/main/resources";
	public static final String FILE_EXTENSION = ".pdf";
	public static final String SUBMIT_USER_MESSAGE = "Hi ,\n Your Claim Request Has Been Submitted Successfully \n ";
	public static final String SUBMIT_APPROVER_MESSAGE = "Hi,\n A Mediclaim Request is waiting for your approval \n";
	public static final String SUBMIT_USER_SUBJECT = "MEDICLAIM STATUS";
	public static final String SUBMIT_APPROVER_SUBJECT = "MEDICLAIM APPROVAL REQUEST";
	public static final String COUNTRY = "India";
}
