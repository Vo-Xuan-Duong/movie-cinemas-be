package com.example.movie_cinemas_be.controller;

import com.example.movie_cinemas_be.dtos.ResponseCustom;
import com.example.movie_cinemas_be.dtos.request.MovieRequest;
import com.example.movie_cinemas_be.dtos.request.MovieRequestAdd;
import com.example.movie_cinemas_be.dtos.response.MovieResponse;
import com.example.movie_cinemas_be.entitys.Movie;
import com.example.movie_cinemas_be.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/create")
    public ResponseCustom<MovieResponse> createMovie(@RequestBody MovieRequest movieRequest) {
        return ResponseCustom.<MovieResponse>builder()
                .message("Successfully created movie")
                .data(movieService.createMovie(movieRequest))
                .build();
    }

    @GetMapping("/playing_now")
    public ResponseCustom<List<MovieResponse>> playingNow() {
        return ResponseCustom.<List<MovieResponse>>builder()
                .data(movieService.getAllMoviesStatus(Movie.MovieStatus.DANGCHIEU))
                .build();
    }

    @GetMapping("/upcoming")
    public ResponseCustom<List<MovieResponse>> upcoming() {
        return ResponseCustom.<List<MovieResponse>>builder()
                .data(movieService.getAllMoviesStatus(Movie.MovieStatus.SAPCHIEU))
                .build();
    }

    @GetMapping("/allMovies")
    public ResponseCustom<Page<MovieResponse>> getAllMovies(Pageable pageable) {
        return ResponseCustom.<Page<MovieResponse>>builder()
                .message("Successfully retrieved all movies")
                .data(movieService.getAllMovies(pageable))
                .build();
    }

    @GetMapping("/getMovie/{movie_id}")
    public ResponseCustom<MovieResponse> getMovieById(@PathVariable("movie_id") long movieId) {
        return ResponseCustom.<MovieResponse>builder()
                .message("Successfully retrieved movie with id " + movieId)
                .data(movieService.getMovieById(movieId))
                .build();
    }

    @PutMapping("/update/{movie_id}")
    public ResponseCustom<MovieResponse> updateMovie(@PathVariable("movie_id") long movieId, @RequestBody MovieRequest movieRequestAdd) {
        return ResponseCustom.<MovieResponse>builder()
                .message("Successfully updated movie with id " + movieId)
                .data(movieService.updateMovie(movieId, movieRequestAdd))
                .build();
    }

    @DeleteMapping("/delete/{movie_id}")
    public ResponseCustom<Void> deleteMovie(@PathVariable("movie_id") long movieId) {
        movieService.deleteMovie(movieId);
        return ResponseCustom.<Void>builder()
                .message("Successfully deleted movie with id " + movieId)
                .build();
    }
}
