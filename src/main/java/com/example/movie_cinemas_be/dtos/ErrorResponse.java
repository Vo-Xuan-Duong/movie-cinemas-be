package com.example.movie_cinemas_be.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp = LocalDateTime.now();
    private String code;
    private String message;
    private int status;

}
