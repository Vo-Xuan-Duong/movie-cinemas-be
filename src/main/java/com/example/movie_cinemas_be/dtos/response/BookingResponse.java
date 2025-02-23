package com.example.movie_cinemas_be.dtos.response;

import com.example.movie_cinemas_be.entitys.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponse {
    private long id;
    private UserResponse user;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private List<TicketResponse> tickets;
    private Booking.BookingStatus status;
    private double totalPrice;
}
