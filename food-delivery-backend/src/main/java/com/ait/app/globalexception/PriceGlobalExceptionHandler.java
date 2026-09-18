package com.ait.app.globalexception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ait.app.exception.PriceCustomException;

@ControllerAdvice
public class PriceGlobalExceptionHandler {

	@ExceptionHandler(PriceCustomException.class)
	public ResponseEntity<String> handlePriceException(PriceCustomException e) {

		return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
	}

}
