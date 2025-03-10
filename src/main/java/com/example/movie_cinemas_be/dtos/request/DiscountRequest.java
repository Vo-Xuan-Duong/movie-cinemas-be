package com.example.movie_cinemas_be.dtos.request;

import com.example.movie_cinemas_be.entitys.Discount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountRequest {
    private String code;
    private double quantity;
    private double discountAmount = 0;
    private double discountRate = 0;
    private Discount.Status status;
}
