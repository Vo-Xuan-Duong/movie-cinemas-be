package com.example.movie_cinemas_be.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CinemaResponse {

    private long cinema_id;
    private String cinema_name;
    private String cinema_address;
    private String cinema_address_map;
    private LocalDate cinema_date;
    private List<RoomResponse> rooms;
}
