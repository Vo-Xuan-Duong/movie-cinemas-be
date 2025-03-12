package com.example.movie_cinemas_be.service;

import com.example.movie_cinemas_be.dtos.request.GenreRequest;
import com.example.movie_cinemas_be.entitys.Genre;
import com.example.movie_cinemas_be.reponsitory.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre create(GenreRequest genrerequest) {
        Genre genre = new Genre();
        genre.setName(genrerequest.getName());
        genre.setId(genrerequest.getId());
        return genreRepository.save(genre);
    }

    public Genre getGenreById(long id){
        return genreRepository.findById(id).get();
    }

    public List<Genre> getAllGenres(){
        return genreRepository.findAll();
    }
}
