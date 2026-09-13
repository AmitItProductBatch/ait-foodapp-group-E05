package com.ait.app.globalexception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ait.app.exception.MenuItemAlreadyExsistsException;

public class MenuExceptionHandler {

	@RestControllerAdvice
	public class GlobalExceptionHandler{
		@ExceptionHandler(MenuItemAlreadyExsistsException.class)
		public ResponseEntity<String>handleMenuItemAlreadyExsistsException(MenuItemAlreadyExsistsException ex){
		
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
		}
	}

}
