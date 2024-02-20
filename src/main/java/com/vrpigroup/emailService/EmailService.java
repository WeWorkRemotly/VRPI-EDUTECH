package com.vrpigroup.emailService;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public interface EmailService {
    void sendEmail(String to, String subject, String content);

    void sendEmail(SimpleMailMessage mailMessage);
}
