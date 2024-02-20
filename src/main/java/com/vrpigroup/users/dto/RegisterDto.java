package com.vrpigroup.users.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String name;
    private String email;
    private String password;
    private Long phoneNumber;
}
