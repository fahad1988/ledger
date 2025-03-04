package com.teya.ledger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> validationErrorDetails = new ArrayList<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            String errorDetails = fieldError.getField() + " : " + fieldError.getDefaultMessage();
            validationErrorDetails.add(errorDetails);
        }
        ErrorMessage errorMessage = new ErrorMessage(validationErrorDetails);
        ErrorResponse response = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientFundsException(InsufficientFundsException ex) {
        ErrorMessage errorMessage = new ErrorMessage(Arrays.asList("Insufficient Funds"));
        ErrorResponse response = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleMethodNotAllowedExceptionException(HttpRequestMethodNotSupportedException e) {
        ErrorMessage errorMessage = new ErrorMessage(Arrays.asList("METHOD NOT ALLOWED : " + e.getMessage()));
        ErrorResponse response = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
