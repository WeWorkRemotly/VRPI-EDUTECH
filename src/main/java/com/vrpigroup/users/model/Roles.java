package com.vrpigroup.users.model;
import lombok.Getter;

/**
 * Roles is an enum class that holds the roles of the user.
 * @Author Aman Raj
 * @version 1.0
 * @since 2021-06-22
 * @apiNote This is a roles enum class
 * @Conact Us : amanrashm@gmail.com
 */
@Getter
public enum Roles {
    ROLE_STUDENT("ROLE_STUDENT"),
    ROLE_TEACHER("ROLE_TEACHER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_SUPERADMIN("ROLE_SUPERADMIN"),
    ROLE_COMPANY("ROLE_COMPANY");

    private final String role;

    Roles(String role) {
        this.role = role;
    }
}