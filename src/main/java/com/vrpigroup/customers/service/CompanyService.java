package com.vrpigroup.customers.service;

import com.vrpigroup.customers.model.CompanyModel;
import com.vrpigroup.customers.repositories.CompanyRepo;
import com.vrpigroup.emailService.JavaMailSenderEmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CompanyService implements AuthenticationManager {

    private final CompanyRepo companyRepo;


    private final JavaMailSenderEmailService javaMailSenderEmailService;


    private final PasswordEncoder passwordEncoder;


    private final AuthenticationProvider authenticationProvider;

    public CompanyService(CompanyRepo companyRepo, JavaMailSenderEmailService javaMailSenderEmailService, PasswordEncoder passwordEncoder, AuthenticationProvider authenticationProvider) {
        this.companyRepo = companyRepo;
        this.javaMailSenderEmailService = javaMailSenderEmailService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
    }

    public void generateResetToken(String email) {
        CompanyModel user = companyRepo.findByEmail(email);
        if (user != null) {
            String resetToken = UUID.randomUUID().toString();
            user.setResetToken(resetToken);
            companyRepo.save(user);
            sendResetTokenEmail(user.getEmail(), resetToken);
        }
    }

    public String userSignup(CompanyModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerificationCode(UUID.randomUUID().toString());
        user.setEnabled(false);
        companyRepo.save(user);
        sendVerificationEmail(user);
        return "Successfully signed-up " + user.getId() + " " + user.getFullName();
    }

    private void sendVerificationEmail(CompanyModel user) {
        // Construct and send an email with a verification link
        String verificationLink = "https://yourdomain.com/verify?code=" + user.getVerificationCode();
        String emailContent = "Please click the following link to verify your email: " + verificationLink;
        javaMailSenderEmailService.sendEmail(user.getEmail(), "Email Verification", emailContent);
    }

    public Authentication authenticate(String userName, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, password);
        return authenticationProvider.authenticate(authenticationToken);
    }

    public void resetPassword(String token, String newPassword) {
        CompanyModel user = companyRepo.findByResetToken(token);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetToken(null);
            companyRepo.save(user);
        }
    }

    private void sendResetTokenEmail(String email, String resetToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Reset password for your id " + email);
        message.setText("Click the following link to reset your password: https://yourdomain.com/reset-password?token=" + resetToken);

        javaMailSenderEmailService.send(message);
    }

    /**
     * Attempts to authenticate the passed {@link Authentication} object, returning a
     * fully populated <code>Authentication</code> object (including granted authorities)
     * if successful.
     *
     * @param authentication the authentication request object
     * @return a fully authenticated object including credentials
     * @throws AuthenticationException if authentication fails
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials());
        Authentication authenticatedToken = authenticationProvider.authenticate(authenticationToken);

        authenticatedToken.isAuthenticated();// Additional custom logic if needed

        return authenticatedToken;
    }
}