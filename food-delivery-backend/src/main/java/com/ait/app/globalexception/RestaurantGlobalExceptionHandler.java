package com.ait.app.globalexception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ait.app.exception.RestaurantCustomException;

@ControllerAdvice
public class RestaurantGlobalExceptionHandler {

    @ExceptionHandler(RestaurantCustomException.class)
    public ResponseEntity RestaurantExceptionHandler(RestaurantCustomException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }

}