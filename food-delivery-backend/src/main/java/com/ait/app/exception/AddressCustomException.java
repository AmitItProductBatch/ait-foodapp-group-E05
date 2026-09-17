package com.ait.app.exception;

import org.springframework.http.HttpStatus;

public class AddressCustomException extends RuntimeException {
	
	private String errMsg;
	private HttpStatus status;
	
	public AddressCustomException(String errMsg, HttpStatus status) {
		super(errMsg);
		this.errMsg = errMsg;
		this.status = status;
	}

	public String getErrMsg() {
		return errMsg;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
}
