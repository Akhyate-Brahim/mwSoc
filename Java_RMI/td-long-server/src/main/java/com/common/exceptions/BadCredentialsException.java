package com.common.exceptions;

public class BadCredentialsException extends Exception {

    public BadCredentialsException() {
        super("The provided credentials are incorrect.");
    }

    public BadCredentialsException(String message) {
        super(message);
    }
}