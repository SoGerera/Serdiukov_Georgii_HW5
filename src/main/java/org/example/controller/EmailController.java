package org.example.controller;

import org.example.notification.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> send(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String text) {
        emailService.send(to, subject, text);
        return ResponseEntity.ok("Email sent to " + to);
    }
}
