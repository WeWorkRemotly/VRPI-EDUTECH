package com.vrpigroup.users.model;
public enum Roles {
    ROLES_STUDENT,
    ROLES_TEACHER,
    ROLES_ADMIN,
    ROLES_COMPANY;

    public int[] split(String s) {
        String[] parts = s.split(",");
        int[] result = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            result[i] = Integer.parseInt(parts[i].trim());
        }

        return result;
    }
}