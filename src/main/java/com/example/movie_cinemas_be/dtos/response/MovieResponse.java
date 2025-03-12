package com.example.movie_cinemas_be.dtos.response;

import com.example.movie_cinemas_be.entitys.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {
    private long id;
    private String title;
    private String description;
    private int duration;//thời lượng phim(phút)
    private List<Genre> genres;
    private List<String> cast;
    private float vote_average;
    private double vote_count; //xếp hạng đánh giá phim
    private double popularity; // sự phổ biến
    private Room.RoomType type;
    private Movie.Support support;
    private String language;
    private String year;
    private Movie.MovieStatus status;
    private List<Country> country;
    private List<Compani> companies;
    private String writer;
    private Movie.Certification certification;
    private String backdrop;
    private String poster;
    private String trailer;
    private LocalDate releaseDate;
    private LocalDate endDate;
}
