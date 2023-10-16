package com.common.exceptions;

public class AlreadyUsedOTPException extends Exception {
    public AlreadyUsedOTPException() {
        super("Invalid or used OTP");
    }
    public AlreadyUsedOTPException(String message) {
        super(message);
    }
}
