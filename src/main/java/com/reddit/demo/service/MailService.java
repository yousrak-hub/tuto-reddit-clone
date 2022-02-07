package com.reddit.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.reddit.demo.model.NotificationEmail;
import com.reddit.demo.service.exception.SpringRedditException;

@Service
public class MailService {

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private MailContentBuilder mailContentBuilder;
	private static final Logger logger = LoggerFactory.getLogger(MailService.class);
	
	@Async
	public void sendMail(NotificationEmail notificationEmail){

		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("yousra.kennou@gmail.com");
			messageHelper.setSubject(notificationEmail.getSubject());
			messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
			messageHelper.setTo(notificationEmail.getRecipient());
		};
		try {
			javaMailSender.send(messagePreparator);
			logger.info("Activation email sent!!");
		} catch (MailException e) {
			logger.error("Exception occurred when sending mail", e);
            throw new SpringRedditException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
		}

	}
}
