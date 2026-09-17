package com.ait.app.globalexception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ait.app.exception.CartItemCustomException;
import com.ait.app.exception.AddressCustomException;
import com.ait.app.exception.FeedbackCustomException;
import com.ait.app.exception.UserServiceCustomException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserServiceCustomException.class)
    public ResponseEntity<String> UserExceptionHandler(UserServiceCustomException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(FeedbackCustomException.class)
    public ResponseEntity<String> handleFeedbackException(
            FeedbackCustomException ex) {

        return new ResponseEntity<>(
                ex.getMessage(),
                org.springframework.http.HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AddressCustomException.class)
    public ResponseEntity<String> handleAddressException(AddressCustomException e) {
        return new ResponseEntity<>(e.getErrMsg(), e.getStatus());
    }
}