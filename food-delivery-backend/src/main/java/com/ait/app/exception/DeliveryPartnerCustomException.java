package com.ait.app.exception;

import org.springframework.http.HttpStatus;

public class DeliveryPartnerCustomException extends RuntimeException{
	
	private String errMsg;
	private HttpStatus httpStatus;
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return errMsg;
	}
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public DeliveryPartnerCustomException(String errMsg, HttpStatus httpStatus) {
		this.errMsg = errMsg;
		this.httpStatus = httpStatus;
	}

	

}
