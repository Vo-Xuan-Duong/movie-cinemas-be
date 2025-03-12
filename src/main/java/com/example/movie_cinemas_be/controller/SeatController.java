package com.example.movie_cinemas_be.controller;

import com.example.movie_cinemas_be.dtos.ResponseCustom;
import com.example.movie_cinemas_be.dtos.request.SeatRequest;
import com.example.movie_cinemas_be.dtos.response.SeatResponse;
import com.example.movie_cinemas_be.entitys.Seat;
import com.example.movie_cinemas_be.service.RoomService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getAllSeat_RoomId/{roomId}")
    public  ResponseCustom<List<SeatResponse>> getAllSeat_RoomId(@PathVariable long roomId) {
        return ResponseCustom.<List<SeatResponse>>builder()
                .message("All Available Seats")
                .data(roomService.getAllSeatByRoom(roomId))
                .build();
    }

    @DeleteMapping("/deleteSeat/{seatId}")
    public ResponseCustom<SeatResponse> deleteSeat(@PathVariable long seatId) {
        return ResponseCustom.<SeatResponse>builder()
                .data(roomService.deleteSeatById(seatId))
                .message("Deleted Seat").build();
    }

    @PutMapping("/updateSeat/{seatId}")
    public ResponseCustom<SeatResponse>  updateSeat(@RequestParam String seatType, @PathVariable long seatId) {
        Seat.SeatType  type = Seat.SeatType.valueOf(seatType);
        return  ResponseCustom.<SeatResponse>builder()
                .message("Updated Seat")
                .data(roomService.updateSeatType(seatId, type))
                .build();
    }

    @PostMapping("/createSeatByRoomId/{roomId}")
    public ResponseCustom<SeatResponse>  createSeatByRoomId(@PathVariable long roomId, @RequestBody SeatRequest seatRequest) {
        return ResponseCustom.<SeatResponse>builder()
                .message("Created Seat")
                .data(roomService.createSeatByRoomId(roomId, seatRequest))
                .build();
    }

}
