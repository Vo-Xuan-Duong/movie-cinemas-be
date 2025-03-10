package com.example.movie_cinemas_be.dtos.response;

import com.example.movie_cinemas_be.entitys.Discount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountResponse {
    private long id;
    private String code;
    private double quantity;
    private double discountAmount;
    private double discountRate;
    private Discount.Status status;
    private LocalDate createDate;
}
