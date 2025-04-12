package com.saiayns.sms.notifications.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.saiayns.sms.notifications.NotificationService;

@Service
public class EmailNotificationService implements NotificationService {
	
	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendNotification(String email, String message) {
		
		SimpleMailMessage smMessage = new SimpleMailMessage();
		smMessage.setTo(email);
		smMessage.setSubject("Saiyans School Management System App");
		smMessage.setText(message);
		smMessage.setFrom("saiyans.bm@gmail.com"); // Optional

        mailSender.send(smMessage);
	}



}
