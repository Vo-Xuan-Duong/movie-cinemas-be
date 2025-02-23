package com.example.movie_cinemas_be.mapper;
import com.example.movie_cinemas_be.dtos.request.MovieRequest;
import com.example.movie_cinemas_be.dtos.response.MovieResponse;
import com.example.movie_cinemas_be.entitys.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperMovie {

    MovieResponse mapToMovieResponse(Movie movie);

    Movie mapToMovies(MovieRequest movieRequest);

}
