package com.hcl.mediclaim.util;

public class MediClaimUtil {

	private MediClaimUtil() {

	}

	public static final String SUCCESS = "User Registered Successfully";
	public static final String LOGIN_SUCCESS = "Logged-In Successfully";
	public static final String FILE_EMPTY_EXCEPTION = "Uploaded file is empty";
	public static final String EMAIL_EXCEPTION = "Email not Valid";
	public static final String INVALID_MOBILE_NUMBER_EXCEPTION = "Please enter valid mobile number";
	public static final String USER_NOT_FOUND = "User not found";
	public static final String USER_ALREADY_EXISTS = "User already registered with this email id";
	public static final Integer GENERICSUCCESSCODE = 200;
	public static final String GENERICSUCCESSMESSAGE = "Success";
	public static final Integer GENERICFAILURECODE = 404;

	public static final String SPRING_MAIL_HOST = "smtp.gmail.com";
	public static final String SPRING_MAIL_PORT = "587";
	public static final String SPRING_MAIL_USERNAME = "testemailhackathon@gmail.com";
	public static final String SPRING_MAIL_PASSWORD = "H@ckath0n";

	public static final String SPRING_MAIL_SMTP_AUT = "true";
	public static final String SPRING_MAIL_SMTP_CONNECTIONTIMEOUT = "5000";
	public static final String SPRING_MAIL_SMTP_WRITETIMEOUT = "5000";

	public static final String SPRING_MAIL_SMTP_STARTTLS_ENABLE = "true";

}
