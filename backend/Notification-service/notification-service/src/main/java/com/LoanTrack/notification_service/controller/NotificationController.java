package com.LoanTrack.notification_service.controller;

import com.LoanTrack.notification_service.model.NotificationRequest;
import com.LoanTrack.notification_service.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private EmailService emailService;

    // Futuro: @Autowired private SmsService smsService;

    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        switch (request.getType().toLowerCase()) {
            case "email":
                emailService.sendEmail(request.getRecipient(), request.getSubject(), request.getBody());
                return ResponseEntity.ok("Email enviado");
            case "sms":
                // smsService.sendSms(request.getRecipient(), request.getBody());
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("SMS no implementado aún");
            default:
                return ResponseEntity.badRequest().body("Tipo de notificación inválido");
        }
    }
}
