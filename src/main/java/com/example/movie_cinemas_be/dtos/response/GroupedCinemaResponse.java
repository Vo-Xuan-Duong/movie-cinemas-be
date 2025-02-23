package com.example.movie_cinemas_be.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupedCinemaResponse {
    private long cinema_id;
    private String cinema_name;
    private String cinema_address;
    private String cinema_address_map;
    private List<ShowTimeResponse> showTime;
}
