package com.ait.app.exception;

public class FeedbackCustomException extends RuntimeException {
	
	private String message;
	
	public FeedbackCustomException(String message) {
		this.message = message;
	}
	@Override
	public String getMessage() {
		return message;
	}

	
}
