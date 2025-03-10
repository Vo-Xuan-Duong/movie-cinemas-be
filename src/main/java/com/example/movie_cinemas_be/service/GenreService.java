package com.example.movie_cinemas_be.service;

import com.example.movie_cinemas_be.entitys.Genre;
import com.example.movie_cinemas_be.reponsitory.GenreRepository;
import org.springframework.stereotype.Service;

@Service

public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre getGenreById(long id){
        return genreRepository.findById(id).get();
    }
}
