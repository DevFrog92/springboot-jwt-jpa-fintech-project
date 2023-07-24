package com.fintech.handler;

import com.fintech.dto.ResponseDto;
import com.fintech.exception.CustomUserApiException;
import com.fintech.exception.CustomValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomUserApiException.class)
    public ResponseEntity<?> userApiException(CustomUserApiException e) {
        return new ResponseEntity<>(
                new ResponseDto<>(HttpStatus.BAD_REQUEST.value(),
                        e.getMessage(),
                        null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<?> validationException(CustomValidationException e) {
        return new ResponseEntity<>(
                new ResponseDto<>(
                        HttpStatus.BAD_REQUEST.value(),
                        e.getMessage(),
                        e.getErrMap()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> rootException(Exception e) {
        return new ResponseEntity<>(
                new ResponseDto<>(
                        HttpStatus.BAD_REQUEST.value(),
                        e.getMessage(),
                        null
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
