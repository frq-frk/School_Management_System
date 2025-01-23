package com.saiayns.sms.notifications.impl;

import org.springframework.stereotype.Service;

import com.saiayns.sms.notifications.NotificationService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class SMSNotificationService implements NotificationService {

	@Override
	public void sendNotification(String phoneNumber, String message) {
		Twilio.init("AC8d7841a81dd0064e3c1cab25f6adec08", "4bf731601ea69f630dfb63b3509c5cc0");
		System.out.printf("SMS sent to %s: %s%n", phoneNumber, message);
		Message responseMessage = Message
                .creator(new com.twilio.type.PhoneNumber(phoneNumber),
                    new com.twilio.type.PhoneNumber("+17755103157"),
                    message)
                .create();
		System.out.println(responseMessage.getBody());
	}

}
