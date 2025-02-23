package com.example.movie_cinemas_be.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String cinemaName;
    private String cinemaAddress;

    @Column(length = 500)
    private String cinemaAddressMap;
    private LocalDate cinemaDate = LocalDate.now();
    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();

}
