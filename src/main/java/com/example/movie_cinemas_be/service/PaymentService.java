package com.example.movie_cinemas_be.service;

import com.example.movie_cinemas_be.entitys.Booking;
import com.example.movie_cinemas_be.entitys.Payment;
import com.example.movie_cinemas_be.reponsitory.PaymentRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public boolean processPayment(Booking booking, Payment.PaymentMethod paymentMethod) {
        Payment payment = new  Payment();
        payment.setBooking(booking);
        payment.setUser(booking.getUser());
        payment.setPaymentMethod(paymentMethod);
        payment.setAmount(booking.getTotalPrice());
        paymentRepository.save(payment);

        return true;
    }

    public boolean verifyPayment(long bookingId ) {
        return false;
    }
}
