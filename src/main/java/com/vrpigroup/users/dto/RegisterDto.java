package com.vrpigroup.users.dto;

import lombok.Data;
/**
 * RegisterDto is a class that holds the registration details of the user.
 * @Author Aman Raj
 * @version 1.0
 * @since 2021-06-22
 * @apiNote This is a register dto class
 * @Conact Us : amanrashm@gmail.com
 */
@Data
public class RegisterDto {
    private String name;
    private String email;
    private String password;
    private Long phoneNumber;
    private String fathersName;
    private String address;
    private String userName;
    private String countryCode;
    private String role;
    private String status;
}
