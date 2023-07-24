package com.fintech.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CustomValidationException extends RuntimeException{
    Map<String, String> errMap = new HashMap<>();
    public CustomValidationException() {
    }

    public CustomValidationException(String message) {
        super(message);
    }

    public CustomValidationException(String message, Map<String, String> errMap) {
        super(message);
        this.errMap = errMap;
    }

    public CustomValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomValidationException(Throwable cause) {
        super(cause);
    }

    public CustomValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
