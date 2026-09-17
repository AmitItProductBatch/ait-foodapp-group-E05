package com.ait.app.globalexception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ait.app.exception.CartItemCustomException;
import com.ait.app.exception.FeedbackCustomException;
import com.ait.app.exception.UserServiceCustomException;
import com.github.loki4j.client.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserServiceCustomException.class)
	public ResponseEntity UserExceptionHandler(UserServiceCustomException e) {
		return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
	}
	
	@ExceptionHandler(FeedbackCustomException.class)
	public ResponseEntity<String> handleFeedbackException(
	        FeedbackCustomException ex) {

	    return new ResponseEntity<>(
	            ex.getMessage(),
	            org.springframework.http.HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CartItemCustomException.class)
    public ResponseEntity<String> CartItemExceptionHandler(CartItemCustomException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }

}



