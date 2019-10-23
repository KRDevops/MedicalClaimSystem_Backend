package com.hcl.mediclaim.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

	public String sendEmail(String recepient, String message, String subject) throws MessagingException {
		log.info("sendmail in JavaMailUtil started");
		try {
			MimeMessage msg = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(msg);
	         
	        helper.setText(message);
	        helper.setSubject(subject);
	        helper.setTo(recepient);
	        
	        javaMailSender.send(msg);
	        
	        log.info("sendmail in JavaMailUtil ended");
			log.info("Message sent successfully");
			
	        return MediClaimUtil.SUCCESS;
		}
		catch(Exception e){
			return MediClaimUtil.REJECTED;
		}
        
	}

}
