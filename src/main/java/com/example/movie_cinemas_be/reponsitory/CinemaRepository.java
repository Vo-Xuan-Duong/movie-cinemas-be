package com.example.movie_cinemas_be.reponsitory;

import com.example.movie_cinemas_be.entitys.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    Cinema findByCinemaNameAndCinemaAddress(String cinemaName, String cinemaAddress);
}
