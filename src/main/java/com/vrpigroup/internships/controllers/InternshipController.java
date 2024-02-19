package com.vrpigroup.internships.controllers;

import com.vrpigroup.internships.model.InternshipModel;
import com.vrpigroup.internships.repositories.InternshipRepo;
import com.vrpigroup.internships.service.InternshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/Internship-WFO")
public class InternshipController {

    @Autowired
    private InternshipService internshipService;

    @Autowired
    private InternshipRepo internshipRepo;

    @PostMapping("/Intern-signup")
    @PreAuthorize("hasAuthority('ROLES_STUDENT')")
    public String addCustomer(@RequestBody InternshipModel user) {
        if (internshipRepo.findByUserNameAndEmail(user.getName(), user.getEmail())) {
            return "redirect:signup?error=userExists";
        }
        return internshipService.userSignup(user) + " Customer " + user.getId() + " added successfully";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String userName, @RequestBody String password) {
        Authentication authentication = internshipService.authenticate(userName, password);
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("Successfully logged in");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/request-reset")
    public ResponseEntity<String> requestPasswordReset(@RequestParam String email) {
        internshipService.generateResetToken(email);
        internshipRepo.findByEmail(email);
        return ResponseEntity.ok("Reset link has been shared to you over email "+email+".");
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        internshipService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password reset successfully.");
    }
}
