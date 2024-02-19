package com.vrpigroup.internships.service;

import com.vrpigroup.emailService.JavaMailSenderEmailService;
import com.vrpigroup.internships.model.InternshipModel;
import com.vrpigroup.internships.repositories.InternshipRepo;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InternshipService {



    private final InternshipRepo internshipRepo;


    private final JavaMailSenderEmailService javaMailSenderEmailService;


    private final AuthenticationManager authenticationManager;


    private final PasswordEncoder passwordEncoder;

    public InternshipService(InternshipRepo internshipRepo, JavaMailSenderEmailService javaMailSenderEmailService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.internshipRepo = internshipRepo;
        this.javaMailSenderEmailService = javaMailSenderEmailService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }


    public String userSignup(InternshipModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerificationCode(UUID.randomUUID().toString());
        user.setEnabled(false);
        internshipRepo.save(user);
        /*sendVerificationEmail(user);*/
        return "Successfully signed-up " + user.getId() + " " + user.getName();
    }

    public Authentication authenticate(String userName, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        return authentication;
    }

    public void generateResetToken(String email) {
        InternshipModel user = internshipRepo.findByEmail(email);
        if (user != null) {
            String resetToken = UUID.randomUUID().toString();
            user.setResetToken(resetToken);
            internshipRepo.save(user);
            sendResetTokenEmail(user.getEmail(), resetToken);
        }
    }

    private void sendResetTokenEmail(String email, String resetToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Reset password for your id "+ email);
        message.setText("Click the following link to reset your password: " +
                "http://yourdomain.com/reset-password?token=" + resetToken);

        javaMailSenderEmailService.send(message);
    }

    public void resetPassword(String token, String newPassword) {
        InternshipModel user = internshipRepo.findByResetToken(token);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetToken(null);
            internshipRepo.save(user);
        }
    }
}
