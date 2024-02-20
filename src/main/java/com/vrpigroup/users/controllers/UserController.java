package com.vrpigroup.users.controllers;

import com.vrpigroup.users.dto.LoginDto;
import com.vrpigroup.users.dto.RegisterDto;
import com.vrpigroup.users.model.UserEntity;
import com.vrpigroup.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/vrpi-users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public ResponseEntity<String> register(@RequestBody /*RegisterDto*/UserEntity registerDto) {
        try {
            userService.register(registerDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Signup successful. Verification email sent to " + registerDto.getEmail());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Signup failed: " + e.getMessage());
        }
    }

    @PutMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestParam String email, @RequestParam String otp) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userService.verifyAccount(email, otp));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verification failed: " + e.getMessage());
        }
    }

    @PutMapping("/regenerate-otp")
    public ResponseEntity<String> regenerateOtp(@RequestParam String email) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userService.regenerateOtp(email));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Otp regeneration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.login(loginDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
        }
    }

    @GetMapping("/get-user")
    public ResponseEntity<Map<String, Object>> getUser(@RequestParam String email) {
        return new ResponseEntity<>(userService.getUser(email), HttpStatus.OK);
    }

    @PutMapping("/update-user")
    public ResponseEntity<String> updateUser(@RequestParam String email, @RequestBody Map<String, Object> updates) {
        return new ResponseEntity<>(userService.updateUser(email, updates), HttpStatus.OK);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<String> deleteUser(@RequestParam String email) {
        return new ResponseEntity<>(userService.deleteUser(email), HttpStatus.OK);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/get-verified-users")
    public ResponseEntity<Map<String, Object>> getVerifiedUsers() {
        return new ResponseEntity<>(userService.getVerifiedUsers(), HttpStatus.OK);
    }

    @GetMapping("/get-unverified-users")
    public ResponseEntity<Map<String, Object>> getUnverifiedUsers() {
        return new ResponseEntity<>(userService.getUnverifiedUsers(), HttpStatus.OK);
    }

    @GetMapping("/get-user-by-id")
    public ResponseEntity<Map<String, Object>> getUserById(@RequestParam Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/get-user-by-name")
    public ResponseEntity<Map<String, Object>> getUserByName(@RequestParam String name) {
        return new ResponseEntity<>(userService.getUserByName(name), HttpStatus.OK);
    }

    @GetMapping("/get-user-by-email")
    public ResponseEntity<Map<String, Object>> getUserByEmail(@RequestParam String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/get-user-by-phone")
    public ResponseEntity<Map<String, Object>> getUserByPhone(@RequestParam String phone) {
        return new ResponseEntity<>(userService.getUserByPhone(phone), HttpStatus.OK);
    }

    /*@PostMapping("/login-linkedin")
    public ResponseEntity<String> loginLinkedin(@RequestBody Map<String, Object> linkedinData) {
        return new ResponseEntity<>(userService.loginLinkedin(linkedinData), HttpStatus.OK);
    }*/

    /*@PostMapping("/login-gmail")
    public ResponseEntity<String> loginGmail(@RequestBody Map<String, Object> GmailData) throws FileNotFoundException {
        return new ResponseEntity<>(userService.loginGmail(GmailData), HttpStatus.OK);
    }*/
}