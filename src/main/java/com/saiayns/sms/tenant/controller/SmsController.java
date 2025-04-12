package com.saiayns.sms.tenant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saiayns.sms.notifications.impl.EmailNotificationService;
import com.saiayns.sms.notifications.impl.SMSNotificationService;

@RestController
@RequestMapping("/api/test")
public class SmsController {
	
	@Autowired
	SMSNotificationService smsService;
	
	@Autowired
	EmailNotificationService emailService;
	
	@GetMapping("/sms")
	public void testSMS() {
		smsService.sendNotification("+916281276093", "This is the test sms message");
	}
	
	@GetMapping("/email")
	public void testEmail() {
		emailService.sendNotification("farooq.saiyan@gmail.com", "This is the test mail");
	}
}
