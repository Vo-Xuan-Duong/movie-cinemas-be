package com.example.movie_cinemas_be.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ShowTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //ID phim
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
    //ID phong
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    private LocalDate showDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private double price;

    public ShowTime(long showtime_id){
        this.id = showtime_id;
    }
}
