package com.example.movie_cinemas_be.dtos.request;

import com.example.movie_cinemas_be.entitys.Seat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatRequest {
    private String seatNumber;
    private Seat.SeatType seatType;
}
