package com.hcl.mediclaim.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @since 2019-10-21 This class includes methods for sending custom message as
 *        email to the user existing in mediclaim management system
 *
 */
@Component
public class JavaMailUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(JavaMailUtil.class);


	@Autowired
	JavaMailSender javaMailSender;

	public void sendEmail(String recepient, String message, String subject) {
		LOGGER.info("sendmail in JavaMailUtil started");

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(recepient);

		msg.setSubject(subject);
		msg.setText(message);

		javaMailSender.send(msg);
		LOGGER.info("sendmail in JavaMailUtil ended");
		LOGGER.info("Message sent successfully");

	}

}
