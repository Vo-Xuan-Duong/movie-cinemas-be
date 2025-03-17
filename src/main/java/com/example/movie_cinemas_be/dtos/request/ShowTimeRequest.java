package com.example.movie_cinemas_be.dtos.request;

import com.example.movie_cinemas_be.entitys.ShowTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowTimeRequest {

    private long movieId;
    private long roomId;

    private LocalDate showDate;
    private LocalTime startTime;
    private double price;
    private ShowTime.Status status;
}
