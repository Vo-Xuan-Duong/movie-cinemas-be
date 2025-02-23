package com.example.movie_cinemas_be.controller;

import com.example.movie_cinemas_be.dtos.ResponseCustom;
import com.example.movie_cinemas_be.service.BookingService;
import com.example.movie_cinemas_be.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final BookingService bookingService;
    public PaymentController(PaymentService paymentService, BookingService bookingService) {
        this.paymentService = paymentService;
        this.bookingService = bookingService;
    }

    @PostMapping("/confirm")
    public ResponseCustom confirmPayment(@RequestParam("bookingId") Long bookingId) {
        if (paymentService.verifyPayment(bookingId)) {
            bookingService.confirmBooking(bookingId);
            return ResponseCustom.builder()
                    .message("Thanh toán thành công ,Vé đã được xác nhận")
                    .build();
        }else{
            bookingService.cancelBooking(bookingId);
            return ResponseCustom.builder()
                    .message("Thanh toán thất bai vé đã bị hủy")
                    .build();
        }

    }

}
