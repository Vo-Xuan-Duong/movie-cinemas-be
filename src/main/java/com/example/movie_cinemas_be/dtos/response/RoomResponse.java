package com.example.movie_cinemas_be.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponse {

    private long id;
    private String name;
    private int capacity;
    private String type;
    private LocalDate date;

}
