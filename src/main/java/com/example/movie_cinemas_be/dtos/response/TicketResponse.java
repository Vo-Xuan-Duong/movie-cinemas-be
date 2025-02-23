package com.example.movie_cinemas_be.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketResponse {

    private long id;
    private String username;
    private String seatNumber;
    private String seatType;
    private double price;
    private ShowTimeResponse showTime;
    private LocalDate ticketDate;
}
