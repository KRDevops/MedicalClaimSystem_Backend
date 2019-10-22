package com.hcl.mediclaim.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @since 2019-10-21 This class includes methods for sending custom message as
 *        email to the user existing in mediclaim management system
 *
 */
@Component
@Slf4j
public class JavaMailUtil {

	@Autowired
	JavaMailSender javaMailSender;

	public void sendEmail(String recepient, String message, String subject) {
		log.info("sendmail in JavaMailUtil started");

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(recepient);

		msg.setSubject(subject);
		msg.setText(message);

		javaMailSender.send(msg);
		log.info("sendmail in JavaMailUtil ended");
		log.info("Message sent successfully");

	}

}
