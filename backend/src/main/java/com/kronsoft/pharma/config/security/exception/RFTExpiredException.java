package com.kronsoft.pharma.config.security.exception;

import org.springframework.http.HttpStatus;

public class RFTExpiredException extends RuntimeException {
    public static HttpStatus status = HttpStatus.FORBIDDEN;
    public RFTExpiredException() {
        super("RFT expired");
    }

    public RFTExpiredException(Throwable err) {
        super("RFT expired", err);
    }

    public RFTExpiredException(String errorMessage) {
        super(errorMessage);
    }

    public RFTExpiredException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
