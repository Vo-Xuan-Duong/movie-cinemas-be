package com.example.movie_cinemas_be.dtos.response;

import com.example.movie_cinemas_be.dtos.ResponseCustom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private String message;
    private String url;
}
