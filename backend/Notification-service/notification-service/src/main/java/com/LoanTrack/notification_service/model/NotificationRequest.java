package com.LoanTrack.notification_service.model;


import lombok.Data;

@Data
public class NotificationRequest {
    private String type; // "email" o "sms"
    private String recipient;
    private String subject; // solo aplica para email
    private String body;
}

