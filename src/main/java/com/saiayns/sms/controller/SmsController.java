package com.saiayns.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saiayns.sms.notifications.impl.SMSNotificationService;

@RestController
@RequestMapping("/api/test-sms")
public class SmsController {
	
	@Autowired
	SMSNotificationService smsService;
	
	@GetMapping
	public void testSMS() {
		smsService.sendNotification("+916281276093", "This is the test sms message");
	}
}
