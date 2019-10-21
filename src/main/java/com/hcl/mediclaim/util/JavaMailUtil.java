package com.hcl.mediclaim.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @since 2019-10-16 This class includes methods for sending username, password
 *        as email to the user registering in book management system for first
 *        time.
 *
 */
@Component
public class JavaMailUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(JavaMailUtil.class);

	@Value("${auth}")
	private String auth;

	@Value("${starttls}")
	private String starttls;

	@Value("${host}")
	private String host;

	@Value("${port}")
	private String port;

	@Value("${hostUrl}")
	private String hostUrl;

	@Value("${portNumber}")
	private String portNumber;

	@Value("${gmailPassword}")
	private String gmailPassword;

	@Value("${myEmail}")
	private String myAccountEmail;

	@Value("${message.part3}")
	private String msgPart;

	@Value("${registrationMessage}")
	private String registrationMessage;

	@Value("${message.part2}")
	private String msgPart2;

	private Session session;

	public void setup() {
		Properties properties = new Properties();

		properties.put(auth, true);
		properties.put(starttls, true);
		properties.put(host, hostUrl);
		properties.put(port, portNumber);

		session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(myAccountEmail, gmailPassword);
			}
		});

	}

	/**
	 * 
	 * @param recepient
	 * @param userName
	 * @param password
	 * @throws MessagingException
	 * 
	 * @author Balaji Method used for sending mail with username, password to newly
	 *         registering user in bms
	 */
	public void sendMail(String recepient, String userName, String password, String subject) throws MessagingException {
		LOGGER.info("sendmail in JavaMailUtil - username/password started");
		this.setup();
		registrationMessage = registrationMessage + msgPart + userName + " " + msgPart2 + password + ".";
		Message message = prepareMessage(session, myAccountEmail, recepient, registrationMessage, subject);
		Transport.send(message);
		LOGGER.info("sendmail in JavaMailUtil - username/password ended");
		LOGGER.info("Message sent successfully from sendMail username/password");
	}

	/**
	 * 
	 * 
	 * @param recepient
	 * @param message
	 * @throws MessagingException
	 * 
	 * @author Balaji This method is used to send email to the recepient passed in
	 *         the argument with message specified.
	 */
	public void sendMail(String recepient, String message, String subject) throws MessagingException {
		LOGGER.info("sendmail in JavaMailUtil started");
		this.setup();
		Message mimeMessage = prepareMessage(session, myAccountEmail, recepient, message, subject);
		Transport.send(mimeMessage);
		LOGGER.info("sendmail in JavaMailUtil ended");
		LOGGER.info("Message sent successfully");
	}

	private Message prepareMessage(Session session, String myAccountEmail, String recepient, String message,
			String subject) {
		Message mimeMessage = new MimeMessage(session);
		try {
			mimeMessage.setFrom(new InternetAddress(myAccountEmail));
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			mimeMessage.setSubject(subject);
			mimeMessage.setText(message);
			return mimeMessage;
		} catch (MessagingException e) {
			LOGGER.error(e.getMessage());
		}

		return null;

	}

}
