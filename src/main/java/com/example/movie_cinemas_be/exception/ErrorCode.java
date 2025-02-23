package com.example.movie_cinemas_be.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


public enum ErrorCode {

    UNCAGORIZED_EXCEPTION("ERR001", HttpStatus.INTERNAL_SERVER_ERROR, "Error Uncagorized exception"),
    VALIDATION_EXCEPTION("ERR002", HttpStatus.BAD_REQUEST, "Validation exception"),
    BAD_CREDENTIALS("ERR003", HttpStatus.UNAUTHORIZED, "Bad credentials"),
    NO_DATA_IN_DATABASE("ERR004", HttpStatus.NOT_FOUND, "No data in database"),

    USER_EXISTS_EXCEPTION("ERR005", HttpStatus.CONFLICT, "User already exists"),
    USER_NOT_FOUND("ERR006", HttpStatus.NOT_FOUND, "User Not Found"),
    USER_NOT_EXISTS_EXCEPTION("ERR007", HttpStatus.CONFLICT, "Users does not exist"),
    UNAUTHENTICATED_EXCEPTION("ERR008", HttpStatus.UNAUTHORIZED, "Unauthorized"),
    MOVIE_EXIST_EXCEPTION("ERR009", HttpStatus.CONFLICT, "Movie already exists"),
    MOVIE_NOT_FOUND("ERR010", HttpStatus.NOT_FOUND, "Movies Not Found"),
    CINEMAS_EXITS("ERR011", HttpStatus.CONFLICT, "Cinemas already exists"),
    CINEMA_NOT_FOUND("ERR012", HttpStatus.NOT_FOUND, "Cinemas Not Found"),
    SHOWTIME_NOT_FOUND("ERR013", HttpStatus.NOT_FOUND, "Show time not found"),
    ROOMS_NOT_FOUND("ERR014", HttpStatus.NOT_FOUND, "Rooms Not Found"),
    TICKET_NOT_FOUND("ERR015", HttpStatus.NOT_FOUND, "Tickets Not Found"),
    SHOWTIME_CONFLICT("ERR016", HttpStatus.CONFLICT, "Show time conflict"),
    SEAT_CONFLICT("ERR017", HttpStatus.CONFLICT, "Seat conflict"),
    PAYMENT_FAILED("ERR018", HttpStatus.CONFLICT, "Payment failed"),

    ;
    private String errorCode;
    private HttpStatus status;
    private String errorMessage;

    ErrorCode(String errorCode, HttpStatus status, String errorMessage) {
        this.errorCode = errorCode;
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
