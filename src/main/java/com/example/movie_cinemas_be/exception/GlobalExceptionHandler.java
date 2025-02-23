package com.example.movie_cinemas_be.exception;

import com.example.movie_cinemas_be.dtos.ErrorResponse;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> runtimeExceptionHandler(RuntimeException e) {

        ErrorCode errorCode = ErrorCode.UNCAGORIZED_EXCEPTION;

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(errorCode.getErrorCode());
        errorResponse.setMessage(errorCode.getErrorMessage());
        errorResponse.setStatus(errorCode.getStatus().value());

        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> customExceptionHandler(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(errorCode.getErrorCode());
        errorResponse.setMessage(errorCode.getErrorMessage());
        errorResponse.setStatus(errorCode.getStatus().value());
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ErrorCode errorCode = ErrorCode.VALIDATION_EXCEPTION;
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(errorCode.getErrorCode());
        errorResponse.setMessage(e.getBindingResult().getFieldError().getDefaultMessage());
        errorResponse.setStatus(errorCode.getStatus().value());

        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> elementNotFoundExceptionHandler(NoSuchElementException e) {
        ErrorCode errorCode = ErrorCode.NO_DATA_IN_DATABASE;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .code(errorCode.getErrorCode())
                .message(errorCode.getErrorMessage())
                .status(errorCode.getStatus().value())
                .build();

        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<Object> badCredentialsExceptionHandler(BadCredentialsException e) {
//        ErrorCode errorCode = ErrorCode.BAD_CREDENTIALS;
//        ErrorResponse errorResponse = new ErrorResponse();
//        errorResponse.setCode(errorCode.getErrorCode());
//        errorResponse.setMessage(errorCode.getErrorMessage());
//        errorResponse.setStatus(errorCode.getStatus().value());
//        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
//    }
}
