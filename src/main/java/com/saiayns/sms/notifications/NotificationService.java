package com.saiayns.sms.notifications;

public interface NotificationService {
    void sendNotification(String phoneNumber, String message);
}