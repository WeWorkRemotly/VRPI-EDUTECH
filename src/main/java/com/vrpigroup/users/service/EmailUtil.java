package com.vrpigroup.users.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * EmailUtil is a class that holds the email sending functionality.
 * @Author Aman Raj
 * @version 1.0
 * @since 2021-06-22
 * @apiNote This is a email util class
 * @Email : amanrashm@gmail.com
 */

@Component
public class EmailUtil {

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpEmail(@RequestBody String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Verify OTP");
        mimeMessageHelper.setText("""
        <div>
          <a href="https://vrpigroup.com/vrpi-users/verify-account?email=%s&otp=%s" target="_blank">click link to verify</a>
        </div>
        """.formatted(email, otp), true);
        javaMailSender.send(mimeMessage);
    }

    public void sendConfirmationEmail(String to, String username) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject("Registration Confirmation");
        helper.setCc("amanrashm@gmail.com");
        helper.setText("Dear " + username + ",\n\nThank you for registering with us. Your registration is successful.");
        javaMailSender.send(message);
    }

    public String sendMailWithAttachment(MultipartFile[] file, String to, String[] cc, String subject, String body) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setCc(cc);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body);
            for (int i = 0; i < file.length; i++) {
                mimeMessageHelper.addAttachment(
                        file[i].getOriginalFilename(),
                        new ByteArrayResource(file[i].getBytes()));
            }
            javaMailSender.send(mimeMessage);
            return "mail send";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}