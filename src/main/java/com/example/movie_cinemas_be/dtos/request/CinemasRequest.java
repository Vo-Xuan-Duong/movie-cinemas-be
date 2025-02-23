package com.example.movie_cinemas_be.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CinemasRequest {
    private String cinema_name;
    private String cinema_address;
    private String cinema_address_map;
}
