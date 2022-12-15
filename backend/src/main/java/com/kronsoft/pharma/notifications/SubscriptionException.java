package com.kronsoft.pharma.notifications;

import com.kronsoft.pharma.exception.ApiError;
import org.springframework.http.HttpStatus;

public class SubscriptionException extends RuntimeException {
    public static HttpStatus status = HttpStatus.FAILED_DEPENDENCY;

    public SubscriptionException() {
        super("Subscription could not be done");
    }

    public SubscriptionException(Throwable err) {
        super("Subscription could not be done", err);
    }

    public SubscriptionException(String errorMessage) {
        super(errorMessage);
    }

    public SubscriptionException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
