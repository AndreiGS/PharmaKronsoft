package com.kronsoft.pharma.auth.exception;

import org.springframework.http.HttpStatus;

public class InvalidRoleException extends RuntimeException {
    public static HttpStatus status = HttpStatus.BAD_REQUEST;
    public InvalidRoleException() {
        super("Role does not exist");
    }

    public InvalidRoleException(Throwable err) {
        super("Role does not exist", err);
    }

    public InvalidRoleException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidRoleException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}

