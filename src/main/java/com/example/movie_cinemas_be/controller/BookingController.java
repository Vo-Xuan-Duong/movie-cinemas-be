package com.example.movie_cinemas_be.controller;

import com.example.movie_cinemas_be.dtos.ResponseCustom;
import com.example.movie_cinemas_be.dtos.request.BookingRequest;
import com.example.movie_cinemas_be.dtos.response.BookingResponse;
import com.example.movie_cinemas_be.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseCustom<BookingResponse> createBooking(@RequestBody BookingRequest bookingRequest) {
        return ResponseCustom.<BookingResponse>builder()
                .data(bookingService.createBooking(bookingRequest))
                .message("Booking created successfully")
                .build();
    }

    @GetMapping
    public ResponseCustom<List<BookingResponse>> getAllBooking() {
        return ResponseCustom.<List<BookingResponse>>builder()
                .data(bookingService.getAllBookings())
                .message("Booking list retrieved successfully")
                .build();
    }

    @GetMapping("/{booking_id}")
    public ResponseCustom<BookingResponse> getBookingById(@PathVariable long booking_id) {
        return ResponseCustom.<BookingResponse>builder()
                .data(bookingService.getBookingById(booking_id))
                .message("Booking get by id successfully")
                .build();
    }


}
