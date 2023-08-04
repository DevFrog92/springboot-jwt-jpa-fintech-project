package com.fintech.handler;

import com.fintech.dto.ResponseDto;
import com.fintech.exception.CustomUnAuthorizedException;
import com.fintech.exception.CustomUserApiException;
import com.fintech.exception.CustomValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomUserApiException.class)
    public ResponseEntity<?> userApiException(CustomUserApiException e) {
        log.info("[Exception] User controller api exception", e);
        return createResponseEntity(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                null
        );
    }

    @ExceptionHandler(CustomUnAuthorizedException.class)
    public ResponseEntity<?> unAuthorizedException(CustomUnAuthorizedException e) {
        log.info("[Exception] unAuthorizedException exception", e);
        return createResponseEntity(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                null
        );
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<?> validationException(CustomValidationException e) {
        log.info("[Exception] validation exception", e);
        return createResponseEntity(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                e.getErrMap()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> rootException(Exception e) {
        log.info("[Exception] Internal server error", e);
        return createResponseEntity(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                null
        );
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> noHandlerFoundException(NoHandlerFoundException e) {
        log.info("[Exception] Not found error", e);
        return createResponseEntity(
                HttpStatus.NOT_FOUND,
                e.getMessage(),
                null
        );
    }

    private static ResponseEntity<ResponseDto<Object>> createResponseEntity(
            HttpStatus status,
            String message,
            Object body) {
        return new ResponseEntity<>(
                new ResponseDto<>(
                        status.value(),
                        message,
                        body
                ),
                status
        );
    }
}
