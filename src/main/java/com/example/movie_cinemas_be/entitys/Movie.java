package com.example.movie_cinemas_be.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    private int duration;//thời lượng phim(phút)

    //thể loại
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Genre> genres;

    @Column(columnDefinition = "JSON")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> cast;

    private float vote_average;//xếp hạng đánh giá phim
    private double vote_count;

    private double popularity; // sự phổ biến
    private String type;
    private String language;

    private int year;

    @Enumerated(EnumType.STRING)
    private MovieStatus status;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Compani> companies = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Country> country = new ArrayList<>();

    private String writer;

    @Enumerated(EnumType.STRING)
    private Certification certification;

    private String backdrop;
    private String poster;
    private String trailer;
    private LocalDate releaseDate;
    private LocalDate endDate;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<ShowTime> showTimes;

    public enum MovieStatus{
        DANGCHIEU,
        SAPCHIEU,
        NGUNGCHIEU,
        TAMNGUNG
    }

    public enum Certification{
        P ,//(phổ thông)
        C13 ,//(trên 13 tuổi),
        C16, //(trên 16 tuổi),
        C18 //(trên 18 tuổi)
    }

}
