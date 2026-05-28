package com.smartcart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import com.smartcart.exception.ProductNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(
        ProductNotFoundException ex) {

    ErrorResponse error = new ErrorResponse(
            ex.getMessage(),
            404,
            LocalDateTime.now());

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
}

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex) {

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                500,
                LocalDateTime.now());

        return new ResponseEntity<>(
                error,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

        @ExceptionHandler(
        UnauthorizedAccessException.class)
        public ResponseEntity<?> handleUnauthorized(
        UnauthorizedAccessException ex) {

        return ResponseEntity
                .status(403)
                .body(ex.getMessage());
        }
}