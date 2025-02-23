package com.example.movie_cinemas_be.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String description;

    private int duration;//thời lượng phim(phút)

    @Column(columnDefinition = "JSON")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> genre;//thể loại

    private String rating;//xếp hạng đánh giá phim

    private String director;

    private String posterUrl;

    private String trailerUrl;

    private String releaseDate;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<ShowTime> showTimes;
}
