package com.saiayns.sms.notifications;

public interface NotificationService {
    void sendNotification(String recipientContact, String message);
}