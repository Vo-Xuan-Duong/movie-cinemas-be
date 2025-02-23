package com.example.movie_cinemas_be.service;

import com.example.movie_cinemas_be.dtos.request.CinemasRequest;
import com.example.movie_cinemas_be.dtos.response.CinemaFromShowtime;
import com.example.movie_cinemas_be.dtos.response.CinemaResponse;
import com.example.movie_cinemas_be.dtos.response.SeatResponse;
import com.example.movie_cinemas_be.entitys.Cinema;
import com.example.movie_cinemas_be.exception.CustomException;
import com.example.movie_cinemas_be.exception.ErrorCode;
import com.example.movie_cinemas_be.reponsitory.CinemaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CinemasService {

    private CinemaRepository cinemaRepository;
    private RoomService roomService;

    public CinemasService(CinemaRepository cinemaRepository, RoomService roomService) {
        this.cinemaRepository = cinemaRepository;
        this.roomService = roomService;
    }

    public Page<CinemaResponse> getAllCinemas(Pageable pageable) {
        Page<Cinema> cinemas = cinemaRepository.findAll(pageable);
        if (cinemas.isEmpty()) {
            throw new CustomException(ErrorCode.CINEMA_NOT_FOUND);
        }
        return cinemas.map(cinemas1 -> convertCinemasToCinemaResponse(cinemas1));
    }

    public CinemaResponse getCinemasById(long id) {
        return convertCinemasToCinemaResponse(cinemaRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.CINEMA_NOT_FOUND)));
    }

    public CinemaResponse createCinemas(CinemasRequest cinemasRequest) {
        if(cinemaRepository.findByCinemaNameAndCinemaAddress(cinemasRequest.getCinema_name(), cinemasRequest.getCinema_address()) != null) {
            throw new CustomException(ErrorCode.CINEMAS_EXITS);
        }
        Cinema newCinema = new Cinema();
        newCinema.setCinemaName(cinemasRequest.getCinema_name());
        newCinema.setCinemaAddress(cinemasRequest.getCinema_address());
        newCinema.setCinemaAddressMap(cinemasRequest.getCinema_address_map());


        return convertCinemasToCinemaResponse(cinemaRepository.save(newCinema));
    }

    public CinemaResponse updateCinemas(long cinemas_id,  CinemasRequest cinemasUpdateRequest) {
        Cinema newCinema = cinemaRepository.findById(cinemas_id).orElseThrow(() -> new CustomException(ErrorCode.CINEMA_NOT_FOUND));

        newCinema.setCinemaName(cinemasUpdateRequest.getCinema_name());
        newCinema.setCinemaAddress(cinemasUpdateRequest.getCinema_address());
        newCinema.setCinemaAddressMap(cinemasUpdateRequest.getCinema_address_map());


        return convertCinemasToCinemaResponse(cinemaRepository.save(newCinema));
    }

    public void deleteCinemas(long cinemas_id) {
        cinemaRepository.deleteById(cinemas_id);
    }



    public CinemaResponse convertCinemasToCinemaResponse(Cinema cinema) {
        return CinemaResponse.builder()
                .cinema_id(cinema.getId())
                .cinema_address(cinema.getCinemaAddress())
                .cinema_address_map(cinema.getCinemaAddressMap())
                .cinema_name(cinema.getCinemaName())
                .cinema_date(cinema.getCinemaDate())
                .rooms(cinema.getRooms().stream().map(rooms -> roomService.convertToRoomResponse(rooms)).collect(Collectors.toList()))
                .build();
    }

    public CinemaFromShowtime  convertCinemasToCinema(Cinema cinema) {
        return CinemaFromShowtime.builder()
                .cinema_id(cinema.getId())
                .cinema_address(cinema.getCinemaAddress())
                .cinema_address_map(cinema.getCinemaAddressMap())
                .cinema_name(cinema.getCinemaName())
                .build();
    }


}
