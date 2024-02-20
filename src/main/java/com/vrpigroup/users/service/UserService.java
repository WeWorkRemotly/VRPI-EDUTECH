package com.vrpigroup.users.service;

import com.vrpigroup.users.dto.LoginDto;
import com.vrpigroup.users.model.UserEntity;
import com.vrpigroup.users.repositories.UserRepo;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class UserService{

    @Autowired
    private OtpUtil otpUtil;

    @Autowired
    private EmailUtil emailUtil;

    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepository;



    public String register(UserEntity registerDto) {
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(registerDto.getEmail(), otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send OTP. Please try again.");
        }
        UserEntity user = new UserEntity();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        String hashedPassword = passwordEncoder.encode(registerDto.getPassword());
        user.setPassword(hashedPassword);
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "User registration successful";
    }

    public String verifyAccount(String email, String otp) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        if (user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(),
                LocalDateTime.now()).getSeconds() < (60 * 60 * 24)) {
            user.setActive(true);
            userRepository.save(user);
            return "OTP verified you can login";
        }
        return "Please regenerate otp and try again";
    }

    public String regenerateOtp(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(email, otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "Email sent... please verify account within 15 minute";
    }

    public String login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserEntity user = userRepository.findByEmail(loginDto.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found with this email: " + loginDto.getEmail()));
            if (!user.isActive()) {
                return "Your account is not verified";
            }
            return "Login successful";
        } catch (AuthenticationException e) {
            return "Invalid credentials";
        }
    }


    public Optional<UserEntity> getUserByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    public Map<String, Object> getUserByPhone(String phone) {
        UserEntity userEntity = userRepository.findByPhoneNumber(phone);
        if (userEntity == null) {
            throw new RuntimeException("User not found with this phone: " + phone);
        }
        return getStringObjectMap(userEntity);
    }

    public Map<String, Object> getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        return getStringObjectMap(userEntity);
    }

    @NotNull
    private Map<String, Object> getStringObjectMap(UserEntity userEntity) {
        Map<String, Object> userMap = Map.of(
                "id", userEntity.getId(),
                "name", userEntity.getName(),
                "email", userEntity.getEmail(),
                "password", userEntity.getPassword(),
                "fathersName", userEntity.getFathersName(),
                "address", userEntity.getAddress(),
                "phoneNumber", userEntity.getPhoneNumber(),
                "countryCode", userEntity.getCountryCode(),
                "district", userEntity.getDistrict(),
                "active", userEntity.isActive()
        );
        return userMap;
    }


    public Map<String, Object> getUserByName(String name) {
        UserEntity userEntity = userRepository.findByName(name);
        if (userEntity == null) {
            throw new RuntimeException("User not found with this name: " + name);
        }
        return getStringObjectMap(userEntity);
    }

    public Map<String, Object> getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with this id: " + id));
        return getStringObjectMap(userEntity);
    }


    public Map<String, Object> convertUserEntityToMap(UserEntity userEntity) {
        return getStringObjectMap(userEntity);
    }

    public List<Map<String, Object>> convertUserEntitiesToMaps(List<UserEntity> userEntities) {
        List<Map<String, Object>> userMaps = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            userMaps.add(convertUserEntityToMap(userEntity));
        }
        return userMaps;
    }

    public Map<String, Object> getUnverifiedUsers() {
        var unverifiedUsers = (List<UserEntity>) userRepository.findByActive(false);
        return (Map<String, Object>) convertUserEntitiesToMaps(unverifiedUsers);
    }

    public Map<String, Object> getVerifiedUsers() {
        var verifiedUsers = (List<UserEntity>) userRepository.findByActive(true);
        return (Map<String, Object>) convertUserEntitiesToMaps(verifiedUsers);
    }

    public Map<String, Object> getAllUsers() {
        var allUsers = userRepository.findAll();
        return (Map<String, Object>) convertUserEntitiesToMaps(allUsers);
    }


    public String deleteUser(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        userRepository.delete(user);
        return "User deleted successfully";
    }

    public String updateUser(String email, Map<String, Object> updates) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        if (updates.containsKey("name")) {
            user.setName((String) updates.get("name"));
        }
        if (updates.containsKey("email")) {
            user.setEmail((String) updates.get("email"));
        }
        if (updates.containsKey("password")) {
            user.setPassword((String) updates.get("password"));
        }
        if (updates.containsKey("fathersName")) {
            user.setFathersName((String) updates.get("fathersName"));
        }
        if (updates.containsKey("address")) {
            user.setAddress((String) updates.get("address"));
        }
        if (updates.containsKey("phoneNumber")) {
            user.setPhoneNumber((String) updates.get("phoneNumber"));
        }
        if (updates.containsKey("countryCode")) {
            user.setCountryCode((String) updates.get("countryCode"));
        }
        if (updates.containsKey("district")) {
            user.setDistrict((String) updates.get("district"));
        }
        userRepository.save(user);
        return "User updated successfully";
    }


    public Map<String, Object> getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        return convertEntityToMap(userEntity);
    }

    private Map<String, Object> convertEntityToMap(UserEntity userEntity) {
        Map<String, Object> userMap = new HashMap<>();
        Field[] fields = UserEntity.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                userMap.put(field.getName(), field.get(userEntity));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return userMap;
    }
}