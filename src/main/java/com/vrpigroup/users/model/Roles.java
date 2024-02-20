package com.vrpigroup.users.model;
import lombok.Getter;

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