package com.fintech.exception;

public class CustomUserApiException extends RuntimeException{
    public CustomUserApiException() {
    }

    public CustomUserApiException(String message) {
        super(message);
    }

    public CustomUserApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomUserApiException(Throwable cause) {
        super(cause);
    }

    public CustomUserApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
