package com.example.movie_cinemas_be.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //cinemas_id
    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    private String name;

    private int capacity;

    private String roomType;

    private LocalDate creationDate = LocalDate.now();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Seat> seats;

}
