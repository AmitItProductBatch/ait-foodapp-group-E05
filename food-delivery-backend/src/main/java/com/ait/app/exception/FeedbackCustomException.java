package com.ait.app.exception;

import org.springframework.http.HttpStatus;

public class FeedbackCustomException extends RuntimeException {

    private String message;
    private HttpStatus status;

    public FeedbackCustomException(
            String message,
            HttpStatus status) {

        this.message = message;
        this.status = status;
    }

    public FeedbackCustomException(String message) {
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
