package com.common.exceptions;

public class IncorrectOTPException extends Exception {
    public IncorrectOTPException() {
        super("Invalid or used OTP");
    }
    public IncorrectOTPException(String message) {
        super(message);
    }
}
