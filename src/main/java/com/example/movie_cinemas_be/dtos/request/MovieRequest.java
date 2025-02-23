package com.example.movie_cinemas_be.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest {

    private String movieName;
    private List<String> genre;
    private int duration;
    private String rating;
    private String director;
    private String description;
    private String posterUrl;
    private String trailerUrl;
    private String releaseDate;
}
