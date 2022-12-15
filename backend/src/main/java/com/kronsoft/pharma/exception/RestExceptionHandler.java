package com.kronsoft.pharma.exception;

import com.kronsoft.pharma.auth.exception.InvalidRoleException;
import com.kronsoft.pharma.notifications.SubscriptionException;
import com.kronsoft.pharma.security.exception.RFTExpiredException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    @ExceptionHandler(RFTExpiredException.class)
    protected ResponseEntity<Object> handleSave(RFTExpiredException ex) {
        return buildResponseEntity(new ApiError(RFTExpiredException.status, ex.getMessage(), ex));
    }

    @ExceptionHandler(InvalidRoleException.class)
    protected ResponseEntity<Object> handleSave(InvalidRoleException ex) {
        return buildResponseEntity(new ApiError(InvalidRoleException.status, ex.getMessage(), ex));
    }

    @ExceptionHandler(MalformedJwtException.class)
    protected ResponseEntity<Object> handleSave(MalformedJwtException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.FORBIDDEN, ex.getMessage(), ex));
    }

    @ExceptionHandler(SubscriptionException.class)
    protected ResponseEntity<Object> handleSave(SubscriptionException ex) {
        return buildResponseEntity(new ApiError(SubscriptionException.status, ex.getMessage(), ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
