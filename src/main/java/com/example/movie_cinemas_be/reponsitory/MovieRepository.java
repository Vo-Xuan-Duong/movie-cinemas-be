package com.example.movie_cinemas_be.reponsitory;

import com.example.movie_cinemas_be.entitys.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByTitle(String title);

    Page<Movie> findAll(Pageable pageable);

    List<Movie> findAllByStatus(Movie.MovieStatus status);
}
