package com.ait.app.exception;

public class MenuItemNotFoundException extends RuntimeException {

    public MenuItemNotFoundException(String message) {
        super(message);
    }
}