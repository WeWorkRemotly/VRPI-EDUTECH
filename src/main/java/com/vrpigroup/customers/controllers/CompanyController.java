package com.vrpigroup.customers.controllers;

import com.vrpigroup.customers.model.CompanyModel;
import com.vrpigroup.customers.repositories.CompanyRepo;
import com.vrpigroup.customers.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/companies")
public class CompanyController {

    private final CompanyService companyService;


    private final CompanyRepo companyRepo;

    public CompanyController(CompanyService companyService, CompanyRepo companyRepo) {
        this.companyService = companyService;
        this.companyRepo = companyRepo;
    }


    @PostMapping("/company-signup")
    @PreAuthorize("hasAuthority('ROLES_COMPANY')")
    public String addCustomer(@RequestBody CompanyModel user) {
        if (companyRepo.findByUserNameAndEmail(user.getFullName(),user.getEmail())) {
            return "redirect:signup?error=userExists";
        }
        return companyService.userSignup(user) + " Customer " + user.getId() + " added successfully";
    }

    @PostMapping("/company-login")
    public ResponseEntity<String> login(@RequestBody String userName, @RequestBody String password) {
        Authentication authentication = companyService.authenticate(userName, password);
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("Successfully logged in");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/company-request-reset")
    public ResponseEntity<String> requestPasswordReset(@RequestParam String email) {
        companyService.generateResetToken(email);
        companyRepo.findByEmail(email);
        return ResponseEntity.ok("Reset link has been shared to you over email "+email+".");
    }

    @PostMapping("/company-reset")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        companyService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password reset successfully.");
    }
}
