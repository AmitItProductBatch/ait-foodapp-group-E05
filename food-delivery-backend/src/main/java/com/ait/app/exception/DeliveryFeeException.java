package com.ait.app.exception;

import org.springframework.http.HttpStatus;

public class DeliveryFeeException extends RuntimeException {
	
	  private HttpStatus status;

	    public DeliveryFeeException(String message, HttpStatus status) {
	        super(message);
	        this.status = status;
	    }

	    public HttpStatus getStatus() {
	        return status;
	    }
	}
	
	


