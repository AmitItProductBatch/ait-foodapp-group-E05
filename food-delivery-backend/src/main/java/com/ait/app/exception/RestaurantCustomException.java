package com.ait.app.exception;

import org.springframework.http.HttpStatus;

public class RestaurantCustomException extends RuntimeException {

    private String errMsg;
    private HttpStatus httpStatus;

    @Override
    public String getMessage() {
        return errMsg;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public RestaurantCustomException(String errMsg, HttpStatus httpStatus) {
        this.errMsg = errMsg;
        this.httpStatus = httpStatus;
    }

}