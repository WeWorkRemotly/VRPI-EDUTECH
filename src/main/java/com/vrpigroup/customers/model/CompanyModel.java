package com.vrpigroup.customers.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CompanyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name can't be blank")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    @Column(name = "User_Name", nullable = false)
    private String userName;

    @NotBlank(message = "Password can't be blank")
    @Size(min = 8, message = "Kindly check password ")
    @Pattern(regexp = "[A-Z][0-9]", message = "Your password must be of 8 characters" +
            "your password must contain number , alphabet, symbols")
    private String password;

    @NotBlank(message = "name can't be blank")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    @Column(name = "Company_Name", nullable = false)
    private String companyName;

    @NotBlank(message = "name can't be blank")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    @Column(name = "Full_Name", nullable = false)
    private String fullName;

    @NotBlank(message = "Email can't be blank")
    @Email(message = "Invalid email format")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid email pattern")
    private String email;

    @NotBlank(message = "Phone Number can't be blank")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    @Column(name = "Phone_Number", nullable = false, unique = true)
    private String phoneNumber;

    @NotBlank(message = "name can't be blank")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    @Column(name = "Company_Type", nullable = false)
    private String typeOfCompany;

    @NotBlank(message = "name can't be blank")
    @Column(name = "Service_Name", nullable = false)
    private String serviceLookingFor;

    @NotBlank(message = "Name can't be blank")
    @Size(min = 3, message = "Name must be between 3 and 50 characters")
    @Column(name = "WebSite_Url")
    @Pattern(regexp = "^https?://\\S+$", message = "Invalid URL format")
    private String webSiteUrl;

    @NotBlank(message = "Name can't be blank")
    @Size(min = 3, message = "Name must be between 3 and 50 characters")
    @Column(name = "linkedin_Url")
    @Pattern(regexp = "^https?://\\S+$", message = "Invalid URL format")
    private String linkedinUrl;

    @NotBlank(message = "Verification code can't be blank")
    @Pattern(regexp = "^[A-Za-z0-9]{8}$", message = "Invalid verification code pattern")
    private String verificationCode;

    @Column(name = "Is_Registered")
    private boolean isRegistered;

    private boolean termsAndConditions;

    private boolean enabled;

    private String resetToken;
}
