package com.sampleproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<ErrorResponse> handleExpiredJwt(ExpiredJwtException ex) {
//        ErrorResponse response = ErrorResponse.builder()
//                .success(false)
//                .message("Your session has expired. Please log in again.")
//                .timestamp(LocalDateTime.now())
//                .build();
//
//        return ResponseEntity
//                .status(HttpStatus.UNAUTHORIZED) // Returns 401
//                .body(response);
//    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex){

        ErrorResponse response =
                ErrorResponse.builder()
                        .success(false)
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex){

        String message = ex.getBindingResult()
                        .getFieldErrors()
                        .getFirst()
                        .getDefaultMessage();

        ErrorResponse response =
                ErrorResponse.builder()
                        .success(false)
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

}
