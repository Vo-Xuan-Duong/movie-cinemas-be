package com.example.movie_cinemas_be.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountResponse {
    private long id;
    private String code;
    private double discountAmount;
    private double discountRate;
}
