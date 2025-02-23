package com.example.movie_cinemas_be.controller;

import com.example.movie_cinemas_be.dtos.ResponseCustom;
import com.example.movie_cinemas_be.dtos.request.RoomRequest;
import com.example.movie_cinemas_be.dtos.response.RoomResponse;
import com.example.movie_cinemas_be.dtos.response.SeatResponse;
import com.example.movie_cinemas_be.service.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/create/{cinema_id}")
    public ResponseCustom<RoomResponse> addRoom(@RequestBody RoomRequest roomRequest, @PathVariable int cinema_id) {
        return ResponseCustom.<RoomResponse>builder()
                .data(roomService.addRoom(roomRequest, cinema_id))
                .message("Success add room ")
                .build();
    }

    @GetMapping("/allRoomsByCinemas-id/{cinema_id}")
    public ResponseCustom<List<RoomResponse>> getAllRooms(@PathVariable long cinema_id) {
        return ResponseCustom.<List<RoomResponse>>builder()
                .data(roomService.getAllRoomByCinema(cinema_id))
                .message("Success getAllRooms ")
                .build();
    }

    @GetMapping("/allRoom")
    public ResponseCustom<List<RoomResponse>> getAllRooms() {
        return ResponseCustom.<List<RoomResponse>>builder()
                .message("Success getAllRooms ")
                .data(roomService.getAllRooms())
                .build();
    }

    @PutMapping("/update/{cinema_id}")
    public ResponseCustom<RoomResponse> updateRoom(@PathVariable Long cinema_id , @RequestBody RoomRequest roomRequest) {
        return ResponseCustom.<RoomResponse>builder()
                .message("Success update room ")
                .data(roomService.updateRoom(roomRequest, cinema_id))
                .build();
    }

    @DeleteMapping("/delete/{room_id}")
    public ResponseCustom deleteRoom(@PathVariable int room_id) {
        roomService.deleteRoom(room_id);
        return ResponseCustom.builder()
                .message("Success delete room " + room_id)
                .build();
    }
}
