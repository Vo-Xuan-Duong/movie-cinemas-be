package com.example.movie_cinemas_be.service;

import com.example.movie_cinemas_be.dtos.request.MovieRequest;
import com.example.movie_cinemas_be.dtos.request.MovieRequestAdd;
import com.example.movie_cinemas_be.dtos.response.MovieResponse;
import com.example.movie_cinemas_be.entitys.Genre;
import com.example.movie_cinemas_be.entitys.Movie;
import com.example.movie_cinemas_be.exception.CustomException;
import com.example.movie_cinemas_be.exception.ErrorCode;
import com.example.movie_cinemas_be.mapper.MapperMovie;
import com.example.movie_cinemas_be.reponsitory.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MapperMovie mapperMovie;
    private final GenreService genreService;
    private final MovieCreateService movieCreateService;

    public MovieService(MovieRepository movieRepository, MapperMovie mapperMovie, GenreService genreService, MovieCreateService movieCreateService) {
        this.movieRepository = movieRepository;
        this.mapperMovie = mapperMovie;
        this.genreService = genreService;
        this.movieCreateService = movieCreateService;
    }

    public MovieResponse updateDataMovie(MovieRequestAdd movieRequestAdd) {

        if(movieRepository.findByTitle(movieRequestAdd.getTitle()).isPresent()) {

            throw new CustomException(ErrorCode.MOVIE_EXIST_EXCEPTION);
        }
        Movie movie = new  Movie();
        movie.setTitle(movieRequestAdd.getTitle());
        movie.setBackdrop(movieRequestAdd.getBackdrop());
        movie.setPoster(movieRequestAdd.getPoster());
        movie.setReleaseDate(movieRequestAdd.getReleaseDate());
        movie.setCast(movieRequestAdd.getCast());
        movie.setCompanies(movieRequestAdd.getCompanies().stream().map(companiResquest -> movieCreateService.createCompani(companiResquest)).collect(Collectors.toList()));
        movie.setCountry(movieRequestAdd.getCountry().stream().map(countryResquest -> movieCreateService.createCountry(countryResquest)).collect(Collectors.toList()));
        movie.setCertification(movieRequestAdd.getCertification());
        movie.setDescription(movieRequestAdd.getDescription());
        movie.setDuration(movieRequestAdd.getDuration());
        List<Genre> genres = movieRequestAdd.getGenres().stream().map(genreId -> genreService.getGenreById(genreId)).collect(Collectors.toList());
        movie.setGenres(genres);
        movie.setPopularity(movieRequestAdd.getPopularity());
        movie.setPoster(movieRequestAdd.getPoster());
        movie.setReleaseDate(movieRequestAdd.getReleaseDate());
        movie.setLanguage( movieRequestAdd.getLanguage());
        movie.setYear(movieRequestAdd.getReleaseDate().getYear());
        movie.setCertification(movieRequestAdd.getCertification());
        movie.setVote_average(movieRequestAdd.getVote_average());
        movie.setVote_count(movieRequestAdd.getVote_count());
        movie.setTrailer(movieRequestAdd.getTrailer());
        movie.setSupport(movieRequestAdd.getSupport());
        movie.setType(movieRequestAdd.getType());
        movie.setStatus(movieRequestAdd.getStatus());

        log.info("Creating movie successful");
        return mapperMovie.mapToMovieResponse(movieRepository.save(movie));
    }

    public MovieResponse createMovie(MovieRequest movieRequestAdd) {

        if(movieRepository.findByTitle(movieRequestAdd.getTitle()).isPresent()) {

            throw new CustomException(ErrorCode.MOVIE_EXIST_EXCEPTION);
        }
        Movie movie = new  Movie();
        movie.setTitle(movieRequestAdd.getTitle());
        movie.setBackdrop(movieRequestAdd.getBackdrop());
        movie.setPoster(movieRequestAdd.getPoster());
        movie.setReleaseDate(movieRequestAdd.getReleaseDate());
        movie.setCast(movieRequestAdd.getCast());
        movie.setCompanies(movieRequestAdd.getCompanies().stream().map(companiResquest -> movieCreateService.getCompaniById(companiResquest)).collect(Collectors.toList()));
        movie.setCountry(movieRequestAdd.getCountry().stream().map(countryResquest -> movieCreateService.getCountryById(countryResquest)).collect(Collectors.toList()));
        movie.setCertification(movieRequestAdd.getCertification());
        movie.setDescription(movieRequestAdd.getDescription());
        movie.setDuration(movieRequestAdd.getDuration());
        List<Genre> genres = movieRequestAdd.getGenres().stream().map(genreId -> genreService.getGenreById(genreId)).collect(Collectors.toList());
        movie.setGenres(genres);
        movie.setPoster(movieRequestAdd.getPoster());
        movie.setReleaseDate(movieRequestAdd.getReleaseDate());
        movie.setLanguage( movieRequestAdd.getLanguage());
        movie.setYear(movieRequestAdd.getReleaseDate().getYear());
        movie.setCertification(movieRequestAdd.getCertification());
        movie.setVote_average(movieRequestAdd.getVote_average());
        movie.setVote_count(movieRequestAdd.getVote_count());
        movie.setTrailer(movieRequestAdd.getTrailer());
        movie.setSupport(movieRequestAdd.getSupport());
        movie.setType(movieRequestAdd.getType());
        movie.setStatus(movieRequestAdd.getStatus());

        log.info("Creating movie successful");
        return mapperMovie.mapToMovieResponse(movieRepository.save(movie));
    }

    public Page<MovieResponse> getAllMovies(Pageable  pageable) {
        Page<Movie> movies = movieRepository.findAll(pageable);
        if(movies.isEmpty()) {
            throw new CustomException(ErrorCode.MOVIE_NOT_FOUND);
        }
        return movies.map(mapperMovie::mapToMovieResponse);
    }

    public List<MovieResponse> getAllMoviesStatus(Movie.MovieStatus status) {
        List<Movie> movie = movieRepository.findAllByStatus(status);
        return movie.stream().map(mapperMovie::mapToMovieResponse).collect(Collectors.toList());
    }

    public MovieResponse getMovieById(long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.MOVIE_NOT_FOUND));
        return mapperMovie.mapToMovieResponse(movie);
    }

    public MovieResponse updateMovie(long id , MovieRequest movieRequestAdd) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.MOVIE_NOT_FOUND));

        movie.setTitle(movieRequestAdd.getTitle());
        movie.setBackdrop(movieRequestAdd.getBackdrop());
        movie.setPoster(movieRequestAdd.getPoster());
        movie.setReleaseDate(movieRequestAdd.getReleaseDate());
        movie.setCast(movieRequestAdd.getCast());
        movie.setCompanies(movieRequestAdd.getCompanies().stream().map(companiResquest -> movieCreateService.getCompaniById(companiResquest)).collect(Collectors.toList()));
        movie.setCountry(movieRequestAdd.getCountry().stream().map(countryResquest -> movieCreateService.getCountryById(countryResquest)).collect(Collectors.toList()));
        movie.setCertification(movieRequestAdd.getCertification());
        movie.setDescription(movieRequestAdd.getDescription());
        movie.setDuration(movieRequestAdd.getDuration());
        List<Genre> genres = movieRequestAdd.getGenres().stream().map(genreId -> genreService.getGenreById(genreId)).collect(Collectors.toList());
        movie.setGenres(genres);
        movie.setPoster(movieRequestAdd.getPoster());
        movie.setReleaseDate(movieRequestAdd.getReleaseDate());
        movie.setLanguage( movieRequestAdd.getLanguage());
        movie.setYear(movieRequestAdd.getReleaseDate().getYear());
        movie.setCertification(movieRequestAdd.getCertification());
        movie.setVote_average(movieRequestAdd.getVote_average());
        movie.setVote_count(movieRequestAdd.getVote_count());
        movie.setTrailer(movieRequestAdd.getTrailer());
        movie.setSupport(movieRequestAdd.getSupport());
        movie.setType(movieRequestAdd.getType());
        movie.setStatus(movieRequestAdd.getStatus());

        return mapperMovie.mapToMovieResponse(movieRepository.save(movie));
    }

    public void deleteMovie(long id) {
        movieRepository.deleteById(id);
    }
}
