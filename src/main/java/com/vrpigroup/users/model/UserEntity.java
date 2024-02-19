package com.vrpigroup.users.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vrpigroup.edtech.students.model.students;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@Table(name = "USER_DETAILS")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_ID")
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
    @Column(name = "Full_Name", nullable = false)
    private String name;

    @NotBlank(message = "Email can't be blank")
    @Email(message = "Invalid email format")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid email pattern")
    private String email;

    @NotBlank(message = "Father's name can't be blank")
    @Size(min = 3, max = 50, message = "Father's name must be between 3 and 50 characters")
    @Column(name = "Fathers_Name", nullable = false)
    private String fathersName;

    @NotBlank(message = "Address can't be blank")
    @Size(max = 255, message = "Address can't exceed 255 characters")
    @Column(name = "ADDRESS", nullable = false)
    private String address;

    @NotBlank(message = "Phone Number can't be blank")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    @Column(name = "Phone_Number", nullable = false, unique = true)
    private String phoneNumber;

    @NotBlank(message = "Country Code can't be blank")
    @Pattern(regexp = "\\+[0-9]+", message = "Invalid country code format")
    @Column(name = "Country_Code", nullable = false)
    private String countryCode;

    @NotBlank(message = "District can't be blank")
    @Size(min = 3, max = 50, message = "District must be between 3 and 50 characters")
    @Column(name = "District", nullable = false)
    private String district;

    @Past
    @NotBlank
    @NotBlank(message = "Date Of Birth can't be blank")
    @Column(name = " DOB", nullable = false)
    private LocalDate dateOfBirth;

    @NotBlank(message = "Pincode can't be blank")
    @Pattern(regexp = "\\d{6}", message = "Invalid pincode format")
    @Column(name = "Pincode", nullable = false)
    private String pincode;

    @NotBlank(message = "pan Card Number can't be blank")
    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid PAN card format")
    @Column(name = "Pan_Card_Number", unique = true)
    private String panCardNumber;

    @NotBlank(message = "Aadhar Card Number can't be blank")
    @Pattern(regexp = "\\d{12}", message = "Invalid Aadhar card format")
    @Column(name = "Aadhar_Card_Number", nullable = false, unique = true)
    private String aadharCardNumber;

    @NotBlank(message = "Verification code can't be blank")
    @Pattern(regexp = "^[A-Za-z0-9]{8}$", message = "Invalid verification code pattern")
    private String verificationCode;

    private boolean enabled;

    private String resetToken;

    @OneToOne(mappedBy = "user", optional = false)
    @JsonIgnore
    private students students;

    @Column(name ="Roles")
    @Enumerated(EnumType.STRING)
    private Roles roles;

}
