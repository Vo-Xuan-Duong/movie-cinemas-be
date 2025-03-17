package com.example.movie_cinemas_be.dtos.response;


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
public class ShowTimeResponse {

    private long id;

    private CinemaFromShowtime  cinema;

    private MovieResponse movie;

    private RoomResponse room;

    private LocalDate show_date;

    private LocalTime start_time;

    private LocalTime end_time;

    private double price;

    private ShowTime.Status status;

}
