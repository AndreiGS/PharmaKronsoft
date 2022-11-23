package com.kronsoft.pharma.auth.role.exeption;

import org.springframework.http.HttpStatus;

public class NotRoleException extends RuntimeException {
    public static HttpStatus status = HttpStatus.BAD_REQUEST;
    public NotRoleException() {
        super("Role does not exist");
    }

    public NotRoleException(Throwable err) {
        super("Role does not exist", err);
    }

    public NotRoleException(String errorMessage) {
        super(errorMessage);
    }

    public NotRoleException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}

