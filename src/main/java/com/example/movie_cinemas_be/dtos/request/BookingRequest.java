package com.example.movie_cinemas_be.dtos.request;

import com.example.movie_cinemas_be.entitys.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private long userId;
    private long showTimeId;
    private List<Long> seatIds;
    private Payment.PaymentMethod paymentMethod;
    private String discountCode;
}
