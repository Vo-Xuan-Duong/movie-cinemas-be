package com.example.movie_cinemas_be.controller;

import com.example.movie_cinemas_be.dtos.ResponseCustom;
import com.example.movie_cinemas_be.dtos.request.ShowTimeRequest;
import com.example.movie_cinemas_be.dtos.response.GroupedCinemaResponse;
import com.example.movie_cinemas_be.dtos.response.ShowTimeResponse;
import com.example.movie_cinemas_be.service.ShowTimeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/showtime")
public class ShowTimeController {
    private final ShowTimeService showTimeService;

    public ShowTimeController(ShowTimeService showTimeService) {
        this.showTimeService = showTimeService;
    }

    @GetMapping("/getShowTineByRoomId/{room_id}")
    public ResponseCustom<Page<ShowTimeResponse>> getAllByRoomId(@PathVariable("room_id") long room_id,  Pageable pageable) {
        return ResponseCustom.<Page<ShowTimeResponse>>builder()
                .data(showTimeService.getAllShowTimesByRoomId(room_id, pageable))
                .message("Success getAllByRoomId " + room_id)
                .build();
    }

    @GetMapping("/filterShowtime")
    public ResponseCustom<Page<ShowTimeResponse>> getAllShowTimeSFilter(
            @RequestParam(value = "roomId", defaultValue = "0") long roomId,
            @RequestParam(value = "date", required = false) LocalDate date,
            Pageable pageable) {
        if (roomId == 0 && date != null) {
            return ResponseCustom.<Page<ShowTimeResponse>>builder()
                    .data(showTimeService.getShowTimesByShowDate(date, pageable))
                    .message("Success")
                    .build();
        }
        else if (roomId != 0 && date == null) {
            return ResponseCustom.<Page<ShowTimeResponse>>builder()
                    .data(showTimeService.getAllShowTimesByRoomId(roomId, pageable))
                    .message("Success ")
                    .build();
        } else if (roomId != 0 && date != null) {
            return ResponseCustom.<Page<ShowTimeResponse>>builder()
                    .data(showTimeService.getShowTimesByRoomIdAndShowDate(roomId, date, pageable))
                    .message("Success ")
                    .build();
        }
        else {
            return ResponseCustom.<Page<ShowTimeResponse>>builder()
                    .message("Failse ")
                    .build();
        }
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

    @GetMapping("/allShowTimes")
    public ResponseCustom<Page<ShowTimeResponse>> getAllShowTimes(Pageable pageable) {
        return ResponseCustom.<Page<ShowTimeResponse>>builder()
                .message("Success getAllShowTimes")
                .data(showTimeService.getAllShowTimes(pageable))
                .build();
    }

    @GetMapping("/searchShowtime")
    public  ResponseCustom<Page<ShowTimeResponse>> getSearchShowTimes(@RequestParam("roomId") Long roomId, @RequestParam("showDate") LocalDate showDate, Pageable pageable) {
        return ResponseCustom.<Page<ShowTimeResponse>>builder()
                .message("Success getSearchShowTimes")
                .data(showTimeService.getShowTimesByRoomIdAndShowDate(roomId, showDate , pageable))
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
