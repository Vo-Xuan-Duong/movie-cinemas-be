package com.example.movie_cinemas_be.controller;

import com.example.movie_cinemas_be.dtos.ResponseCustom;
import com.example.movie_cinemas_be.dtos.request.ShowTimeRequest;
import com.example.movie_cinemas_be.dtos.response.GroupedCinemaResponse;
import com.example.movie_cinemas_be.dtos.response.ShowTimeResponse;
import com.example.movie_cinemas_be.service.ShowTimeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/showtime")
public class ShowTimeController {
    private final ShowTimeService showTimeService;

    public ShowTimeController(ShowTimeService showTimeService) {
        this.showTimeService = showTimeService;
    }

    @GetMapping("/room/{room_id}")
    public ResponseCustom<List<ShowTimeResponse>> getAllByRoomId(@PathVariable("room_id") long room_id) {
        return ResponseCustom.<List<ShowTimeResponse>>builder()
                .data(showTimeService.getAllShowTimesByRoomId(room_id))
                .message("Success getAllByRoomId " + room_id)
                .build();
    }

    @GetMapping("/getByMovie/{movie_id}")
    public ResponseCustom<List<GroupedCinemaResponse>> getAllByMovieId(@PathVariable("movie_id") long movie_id) {
        return ResponseCustom.<List<GroupedCinemaResponse>>builder()
                .message("Success getAllByMovieId " + movie_id)
                .data(showTimeService.getAllShowTimesByMovieId(movie_id))
                .build();
    }

    @PostMapping("/create")
    public ResponseCustom<ShowTimeResponse> addShowTime(@RequestBody ShowTimeRequest showTimeRequest) {
        return ResponseCustom.<ShowTimeResponse>builder()
                .message("add ShowTime Success")
                .data(showTimeService.addShowTime(showTimeRequest))
                .build();
    }

    @GetMapping("/allShowTime")
    public ResponseCustom<List<ShowTimeResponse>> getAllShowTimes() {
        return ResponseCustom.<List<ShowTimeResponse>>builder()
                .message("Success getAllShowTimes")
                .data(showTimeService.getAllShowTimes())
                .build();
    }

    @PutMapping("/update/{showTime_id}")
    public ResponseCustom<ShowTimeResponse> updateShowTime(@PathVariable Long showTime_id, @RequestBody ShowTimeRequest showTimeRequest){
        return ResponseCustom.<ShowTimeResponse>builder()
                .data(showTimeService.updateShowTime(showTime_id, showTimeRequest))
                .build();
    }

    @DeleteMapping("/delete/{showTime_id}")
    public ResponseCustom deleteShowTime(@PathVariable Long showTime_id){
        showTimeService.deleteShowTime(showTime_id);
        return ResponseCustom.builder()
                .message("deleted successfully showTime by id = " + showTime_id)
                .build();
    }
}
