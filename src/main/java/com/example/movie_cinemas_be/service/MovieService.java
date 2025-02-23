package com.example.movie_cinemas_be.service;

import com.example.movie_cinemas_be.dtos.request.MovieRequest;
import com.example.movie_cinemas_be.dtos.response.MovieResponse;
import com.example.movie_cinemas_be.entitys.Movie;
import com.example.movie_cinemas_be.exception.CustomException;
import com.example.movie_cinemas_be.exception.ErrorCode;
import com.example.movie_cinemas_be.mapper.MapperMovie;
import com.example.movie_cinemas_be.reponsitory.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MapperMovie mapperMovie;

    public MovieService(MovieRepository movieRepository, MapperMovie mapperMovie) {
        this.movieRepository = movieRepository;
        this.mapperMovie = mapperMovie;
    }

    public MovieResponse createMovie(MovieRequest movieRequest) {
        if(movieRepository.findByTitle(movieRequest.getMovieName()).isPresent()) {
            throw new CustomException(ErrorCode.MOVIE_EXIST_EXCEPTION);
        }
        Movie movie = mapperMovie.mapToMovies(movieRequest);
        return mapperMovie.mapToMovieResponse(movie);
    }

    public Page<MovieResponse> getAllMovies(Pageable  pageable) {
        Page<Movie> movies = movieRepository.findAll(pageable);
        if(movies.isEmpty()) {
            throw new CustomException(ErrorCode.MOVIE_NOT_FOUND);
        }
        return movies.map(mapperMovie::mapToMovieResponse);
    }

    public MovieResponse getMovieById(long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.MOVIE_NOT_FOUND));
        return mapperMovie.mapToMovieResponse(movie);
    }

    public MovieResponse updateMovie(long id , MovieRequest movieUpdateRequest) {
        Movie updateMovie = movieRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.MOVIE_NOT_FOUND));
        updateMovie.setTitle(movieUpdateRequest.getMovieName());
        updateMovie.setDirector(movieUpdateRequest.getDirector());
        updateMovie.setGenre(movieUpdateRequest.getGenre());
        updateMovie.setDescription(movieUpdateRequest.getDescription());
        updateMovie.setRating(movieUpdateRequest.getRating());
        updateMovie.setPosterUrl(movieUpdateRequest.getPosterUrl());
        updateMovie.setTrailerUrl(movieUpdateRequest.getTrailerUrl());
        updateMovie.setReleaseDate(movieUpdateRequest.getReleaseDate());
        return mapperMovie.mapToMovieResponse(movieRepository.save(updateMovie));
    }

    public void deleteMovie(long id) {
        movieRepository.deleteById(id);
    }
}
