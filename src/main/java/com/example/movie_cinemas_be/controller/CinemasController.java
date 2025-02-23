package com.example.movie_cinemas_be.controller;

import com.example.movie_cinemas_be.dtos.ResponseCustom;
import com.example.movie_cinemas_be.dtos.request.CinemasRequest;
import com.example.movie_cinemas_be.dtos.response.CinemaResponse;
import com.example.movie_cinemas_be.service.CinemasService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cinemas")
public class CinemasController {

    private final CinemasService cinemasService;

    public CinemasController(CinemasService cinemasService) {
        this.cinemasService = cinemasService;
    }

    @PostMapping("/create")
    public ResponseCustom<CinemaResponse> addCinemas(@RequestBody CinemasRequest cinemasRequest) {
        return ResponseCustom.<CinemaResponse>builder()
                .data(cinemasService.createCinemas(cinemasRequest))
                .message("Successfully added cinemas")
                .build();

    }

    @GetMapping("/getAll")
    public ResponseCustom<Page<CinemaResponse>> getAllCinemas(Pageable  pageable) {
        return ResponseCustom.<Page<CinemaResponse>>builder()
                .data(cinemasService.getAllCinemas(pageable))
                .message("Successfully retrieved all cinemas")
                .build();
    }

    @GetMapping("/get_cinema/{cinema_id}")
    public ResponseCustom<CinemaResponse> showCinema(@PathVariable Long cinema_id) {
        return ResponseCustom.<CinemaResponse>builder()
                .message("Successfully retrieved cinema")
                .data(cinemasService.getCinemasById(cinema_id))
                .build();
    }

    @PutMapping("/update/{cinema_id}")
    public ResponseCustom<CinemaResponse> updateCinema(@PathVariable Long cinema_id, @RequestBody CinemasRequest cinemasRequest) {
        return ResponseCustom.<CinemaResponse>builder()
                .message("Successfully updated cinema")
                .data(cinemasService.updateCinemas(cinema_id, cinemasRequest))
                .build();
    }

    @DeleteMapping("/delete/{cinema_id}")
    public ResponseCustom deleteCinema(@PathVariable Long cinema_id) {
        cinemasService.deleteCinemas(cinema_id);
        return ResponseCustom.builder()
                .message("Successfully deleted cinema")
                .build();
    }
}
