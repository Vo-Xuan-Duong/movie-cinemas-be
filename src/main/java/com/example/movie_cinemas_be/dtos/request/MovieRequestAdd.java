package com.example.movie_cinemas_be.dtos.request;

import com.example.movie_cinemas_be.entitys.Movie;
import com.example.movie_cinemas_be.entitys.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequestAdd {

    private String title;
    private String description;
    private int duration;//thời lượng phim(phút)
    private List<Long> genres;
    private List<String> cast;
    private float vote_average;
    private double vote_count; //xếp hạng đánh giá phim
    private double popularity; // sự phổ biến
    private Room.RoomType type;
    private Movie.Support support;
    private String language;
    private List<CountryResquestAdd> country = new ArrayList<>();
    private List<CompaniResquestAdd> companies =  new ArrayList<>();
    private String writer;
    private Movie.Certification certification;
    private String backdrop;
    private String poster;
    private String trailer;
    private LocalDate releaseDate;
    private Movie.MovieStatus status;
}
