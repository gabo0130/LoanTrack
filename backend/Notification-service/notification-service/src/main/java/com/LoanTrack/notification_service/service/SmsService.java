package com.LoanTrack.notification_service.service;

import org.springframework.stereotype.Service;

@Service
public class SmsService {
    public void sendSms(String phoneNumber, String message) {
        // Integrar con Twilio u otro proveedor
    }
}
