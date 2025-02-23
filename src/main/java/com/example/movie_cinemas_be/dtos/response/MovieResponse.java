package com.example.movie_cinemas_be.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {
    private long id;
    private String title;
    private String description;
    private String director;
    private List<String> genre;
    private int duration;
    private String rating;
    private String posterUrl;
    private String trailerUrl;
    private String releaseDate;
}
