package com.fintech.exception;

public class CustomUnAuthorizedException extends RuntimeException{
    public CustomUnAuthorizedException(String message) {
        super(message);
    }
}
