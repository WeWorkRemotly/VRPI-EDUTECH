package com.vrpigroup.emailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class JavaMailSenderEmailService implements EmailService {


    private final JavaMailSender javaMailSender;

    public JavaMailSenderEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
    }

    public void send(SimpleMailMessage message) {
        javaMailSender.send(message);
    }
}