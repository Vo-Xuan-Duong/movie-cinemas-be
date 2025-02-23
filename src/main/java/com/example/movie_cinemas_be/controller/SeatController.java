package com.example.movie_cinemas_be.controller;

import com.example.movie_cinemas_be.dtos.ResponseCustom;
import com.example.movie_cinemas_be.dtos.response.SeatResponse;
import com.example.movie_cinemas_be.service.RoomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seat")
public class SeatController {

    private RoomService roomService;

    public SeatController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/available")
    public ResponseCustom<List<SeatResponse>> getAvailableSeats(
            @RequestParam Long showTimeId
    ) {
        return ResponseCustom.<List<SeatResponse>>builder()
                .data(roomService.getAvailableSeats(showTimeId))
                .message("All Available Seats")
                .build();
    }

    @GetMapping("/allSeatToShowTime")
    public ResponseCustom<List<SeatResponse>> getSeatToShowTime(
            @RequestParam Long showTimeId
    ) {
        return ResponseCustom.<List<SeatResponse>>builder()
                .data(roomService.getAllSeatToShowTime(showTimeId))
                .message("All Available Seats")
                .build();
    }

}
