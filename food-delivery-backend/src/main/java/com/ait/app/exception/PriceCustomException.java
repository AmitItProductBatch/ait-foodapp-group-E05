package com.ait.app.exception;

import org.springframework.http.HttpStatus;

public class PriceCustomException extends RuntimeException {

    private String errMsg;

    private HttpStatus httpStatus;

    public PriceCustomException(
            String errMsg,
            HttpStatus httpStatus) {

        this.errMsg = errMsg;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return errMsg;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}