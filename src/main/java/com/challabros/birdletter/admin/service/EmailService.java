package com.challabros.birdletter.admin.service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.challabros.birdletter.admin.dto.EmailSendDto;

@Service
public class EmailService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
	
	@Autowired
	protected JavaMailSender mailSender;
	
	public void SendEMail(EmailSendDto emailSendDto) throws Exception {
		logger.info("이메일 발송 시작");
		MimeMessage msg = mailSender.createMimeMessage();
		
		try {
			msg.setSubject(emailSendDto.getSubject());
			msg.setText(emailSendDto.getContent());
			msg.setRecipients(MimeMessage.RecipientType.TO, 
						InternetAddress.parse(emailSendDto.getReceiver()));
		} catch (MessagingException e) {
			// TODO: handle exception
			logger.info("MessagingException");
			e.printStackTrace();
		}
		try {
			mailSender.send(msg);
		} catch (MailException e) {
			// TODO: handle exception
			logger.info("MailException발생");
			e.printStackTrace();
		}
		logger.info("이메일 발송 끝");
	}

}
