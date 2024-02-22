package com.vrpigroup.users.dto;

import lombok.Data;
/**
 * LoginDto is a class that holds the login credentials of the user.
 * @Author Aman Raj
 * @version 1.0
 * @since 2021-06-22
 * @apiNote This is a login dto class
 * @Conact Us : amanrashm@gmail.com
 */
@Data
public class LoginDto {

    private String email;
    private String password;
}
