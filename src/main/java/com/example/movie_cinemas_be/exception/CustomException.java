package com.example.movie_cinemas_be.exception;

public class CustomException extends RuntimeException {
    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public CustomException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
