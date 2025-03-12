package com.example.movie_cinemas_be.controller;

import com.example.movie_cinemas_be.dtos.ResponseCustom;
import com.example.movie_cinemas_be.dtos.request.GenreRequest;
import com.example.movie_cinemas_be.entitys.Genre;
import com.example.movie_cinemas_be.service.GenreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/updatedata")
public class UpdateController {

    private final GenreService genreService;

    public UpdateController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping("/genre/create")
    public ResponseCustom<Genre> create(@RequestBody GenreRequest genre) {
        return ResponseCustom.<Genre>builder().data(genreService.create(genre)).build();
    }

    @GetMapping("/genre/getAllGenres")
    public ResponseCustom<List<Genre>> getAllGenres() {
        return ResponseCustom.<List<Genre>>builder()
                .data(genreService.getAllGenres())
                .build();
    }
}
