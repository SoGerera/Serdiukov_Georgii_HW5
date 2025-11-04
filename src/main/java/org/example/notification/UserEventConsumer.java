package org.example.notification;

import org.example.event.UserEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventConsumer {

    private final EmailService emailService;

    public UserEventConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "user-events", groupId = "notification-group")
    public void listen(UserEvent event) {
        String message = switch (event.operation()) {
            case "CREATED" -> "Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.";
            case "DELETED" -> "Здравствуйте! Ваш аккаунт был удалён.";
            default -> "Операция: " + event.operation();
        };
        emailService.send(event.email(), "Уведомление", message);
    }
}
