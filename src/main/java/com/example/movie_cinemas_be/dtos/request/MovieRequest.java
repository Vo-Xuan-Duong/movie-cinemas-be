package com.example.movie_cinemas_be.dtos.request;

import com.example.movie_cinemas_be.entitys.Genre;
import com.example.movie_cinemas_be.entitys.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest {

    private String title;
    private String description;
    private int duration;//thời lượng phim(phút)
    private List<Long> genres;
    private List<String> cast;
    private float vote_average;
    private double vote_count; //xếp hạng đánh giá phim
    private double popularity; // sự phổ biến
    private String type;
    private String language;
    private List<CountryResquest> country = new ArrayList<>();
    private List<CompaniResquest> companies =  new ArrayList<>();
    private String writer;
    private Movie.Certification certification;
    private String backdrop;
    private String poster;
    private String trailer;
    private LocalDate releaseDate;
}
