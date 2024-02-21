package com.vrpigroup.users.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.vrpigroup.annotations.dobAnnotation.ValidDateOfBirth;
import com.vrpigroup.annotations.passwordAnnotation.Password;
import com.vrpigroup.edtechEnrollment.model.CourseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * UserEntity is a class that holds the user details.
 * @Author Aman Raj
 * @version 1.0
 * @since 2021-06-22
 * @apiNote This is a user entity class
 * @Conact Us :amanrashm@gmail.com
 */

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

    //@NotBlank(message = "Name can't be blank")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    @Column(name = "User_Name", nullable = false, unique = true)
    private String userName;

    //@NotBlank(message = "Password can't be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Password
    @Column(name = "Password", nullable = false)
    private String password;

    //@NotBlank(message = "Full Name can't be blank")
    @Size(min = 3, max = 50, message = "Full Name must be between 3 and 50 characters")
    @Column(name = "Full_Name", nullable = false)
    private String name;

    //@NotBlank(message = "Email can't be blank")
    @Email(message = "Invalid email format")
    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    //@NotBlank(message = "Father's name can't be blank")
    @Size(min = 3, max = 50, message = "Father's name must be between 3 and 50 characters")
    @Column(name = "Fathers_Name", nullable = false)
    private String fathersName;

    //@NotBlank(message = "Address can't be blank")
    @Size(max = 255, message = "Address can't exceed 255 characters")
    @Column(name = "ADDRESS", nullable = false)
    private String address;

    //@NotBlank(message = "Phone Number can't be blank")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    @Column(name = "Phone_Number", nullable = false, unique = true)
    private String phoneNumber;

    //@NotBlank(message = "Country Code can't be blank")
    @Pattern(regexp = "\\+[0-9]+", message = "Invalid country code format")
    @Column(name = "Country_Code", nullable = false)
    private String countryCode;

    //@NotBlank(message = "District can't be blank")
    @Size(min = 3, max = 50, message = "District must be between 3 and 50 characters")
    @Column(name = "District", nullable = false)
    private String district;

    @ValidDateOfBirth(message = "Date of birth must be valid and 16+ years old")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "DOB", nullable = false)
    private String dateOfBirth;


    //@NotBlank(message = "Pincode can't be blank")
    @Pattern(regexp = "\\d{6}", message = "Invalid pincode format")
    @Column(name = "Pincode", nullable = false)
    private String pincode;

    //@NotBlank(message = "Pan Card Number can't be blank")
    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid PAN card format")
    @Column(name = "Pan_Card_Number", unique = true)
    private String panCardNumber;

    //@NotBlank(message = "Aadhar Card Number can't be blank")
    @Pattern(regexp = "\\d{12}", message = "Invalid Aadhar card format")
    @Column(name = "Aadhar_Card_Number", nullable = false, unique = true)
    private String aadharCardNumber;

    //@NotBlank(message = "Verification code can't be blank")
    @Pattern(regexp = "^[A-Za-z0-9]{8}$", message = "Invalid verification code pattern")
    @Column(name = "Verification_Code", nullable = false)
    private String verificationCode;

    private String resetToken;

    private boolean active;

    private String otp;

    private LocalDateTime otpGeneratedTime;

    @ElementCollection(targetClass = Roles.class)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;

    @OneToMany(mappedBy = "userEntities", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    @JsonBackReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Transient
    private CourseEntity courseEntity;
}