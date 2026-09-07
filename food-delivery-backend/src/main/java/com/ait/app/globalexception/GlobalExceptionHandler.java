package com.ait.app.globalexception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ait.app.exception.UserServiceCustomException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserServiceCustomException.class)
	public ResponseEntity UserExceptionHandler(UserServiceCustomException e) {
		return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
	}

}
