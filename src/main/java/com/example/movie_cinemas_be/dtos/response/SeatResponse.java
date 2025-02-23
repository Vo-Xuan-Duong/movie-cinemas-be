package com.example.movie_cinemas_be.dtos.response;

import com.example.movie_cinemas_be.entitys.Seat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatResponse {
    private long id;
    private String seatNumber;
    private Seat.SeatType seatType;
    private Seat.SeatStatus seatStatus;
}
