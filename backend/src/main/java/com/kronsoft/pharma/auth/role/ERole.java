package com.kronsoft.pharma.auth.role;

import com.kronsoft.pharma.auth.role.constants.RoleConstants;
import com.kronsoft.pharma.auth.role.exeption.NotRoleException;

public enum ERole {
    ROLE_USER(RoleConstants.ROLE_USER),
    ROLE_MODERATOR(RoleConstants.ROLE_MODERATOR),
    ROLE_ADMIN(RoleConstants.ROLE_ADMIN);

    private final String name;

    ERole(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }

    public static ERole toEnum(String name) {
        switch (name) {
            case RoleConstants.ROLE_USER: return ERole.ROLE_USER;
            case RoleConstants.ROLE_MODERATOR: return ERole.ROLE_MODERATOR;
            case RoleConstants.ROLE_ADMIN: return ERole.ROLE_ADMIN;
        }
        throw new NotRoleException();
    }
}
