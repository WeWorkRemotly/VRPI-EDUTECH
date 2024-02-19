package com.vrpigroup.users.service;

import com.vrpigroup.users.model.UserEntity;
import com.vrpigroup.users.repositories.UserRepo;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.vrpigroup.emailService.JavaMailSenderEmailService;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@NoArgsConstructor
public class UserService implements UserDetails{

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private JavaMailSenderEmailService javaMailSenderEmailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    private String name;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserService(UserEntity userEntity) {
        this.name = userEntity.getName();
        this.password = userEntity.getPassword();
        this.authorities = Arrays.stream(userEntity.getRoles().split(" ,"))
                .mapToObj(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    public UserService(UserRepo userRepository, PasswordEncoder passwordEncoder, String name, String password, List<GrantedAuthority> authorities) {
        this.name = name;
        this.password = password;
        this.authorities = authorities;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public UserService(UserRepo userRepository, PasswordEncoder passwordEncoder) {

        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    public void userSignup(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerificationCode(UUID.randomUUID().toString());
        user.setEnabled(false);
        userRepository.save(user);
        sendVerificationEmail(user);
    }

    private void sendVerificationEmail(UserEntity user) {
        // Construct and send an email with a verification link
        String verificationLink = "https://yourdomain.com/verify?code=" + user.getVerificationCode();
        String emailContent = "Please click the following link to verify your email: " + verificationLink;
        javaMailSenderEmailService.sendEmail(user.getEmail(), "Email Verification", emailContent);
    }

    public String login(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "Successfully logged in";
        } catch (AuthenticationException e) {
            return "Invalid credentials";
        }
    }

    public void generateResetToken(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user != null) {
            String resetToken = UUID.randomUUID().toString();
            user.setResetToken(resetToken);
            userRepository.save(user);
            sendResetTokenEmail(user.getEmail(), resetToken);
        }
    }

    public void resetPassword(String token, String newPassword) {
        UserEntity user = userRepository.findByResetToken(token);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetToken(null);
            userRepository.save(user);
        }
    }

    private void sendResetTokenEmail(String email, String resetToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Reset password for your id " + email);
        message.setText("Click the following link to reset your password: https://yourdomain.com/reset-password?token=" + resetToken);

        javaMailSenderEmailService.send(message);
    }
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new UserService(user);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}