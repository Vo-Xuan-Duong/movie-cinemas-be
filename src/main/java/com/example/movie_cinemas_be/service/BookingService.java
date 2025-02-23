package com.example.movie_cinemas_be.service;

import com.example.movie_cinemas_be.dtos.request.BookingRequest;
import com.example.movie_cinemas_be.dtos.response.BookingResponse;
import com.example.movie_cinemas_be.entitys.*;
import com.example.movie_cinemas_be.exception.CustomException;
import com.example.movie_cinemas_be.exception.ErrorCode;
import com.example.movie_cinemas_be.reponsitory.BookingReponsitory;
import com.example.movie_cinemas_be.reponsitory.SeatRepository;
import com.example.movie_cinemas_be.reponsitory.ShowTimeRepository;
import com.example.movie_cinemas_be.reponsitory.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingReponsitory bookingReponsitory;
    private final PaymentService paymentService;
    private final SeatRepository seatRepository;
    private final ShowTimeRepository showTimeRepository;
    private final DiscountService discountService;
    private final EmailService emailService;
    private UserService userService;
    private TicketService ticketService;
    private UserRepository userRepository;

    public BookingService(BookingReponsitory bookingReponsitory,
                          UserService userService,
                          TicketService ticketService,
                          PaymentService paymentService,
                          SeatRepository seatRepository,
                          ShowTimeRepository showTimeRepository,
                          DiscountService discountService,
                          EmailService emailService,
                          UserRepository userRepository) {
        this.bookingReponsitory = bookingReponsitory;
        this.userService = userService;
        this.ticketService = ticketService;
        this.paymentService = paymentService;
        this.seatRepository = seatRepository;
        this.showTimeRepository = showTimeRepository;
        this.discountService = discountService;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    public List<BookingResponse> getAllBookings() {
        List<Booking> bookings = bookingReponsitory.findAll();
        return  bookings.stream().map(this::convertToBookingResponse).collect(Collectors.toList());
    }

    public BookingResponse getBookingById(Long id) {
        return convertToBookingResponse(bookingReponsitory.findById(id)
                .orElseThrow(() -> new CustomException("Booking not found", ErrorCode.NO_DATA_IN_DATABASE)));
    }

    public BookingResponse createBooking(BookingRequest bookingRequest) {
        List<Seat> selectedSeats = seatRepository.findByIdIn(bookingRequest.getSeatIds());
        if(selectedSeats.size() != bookingRequest.getSeatIds().size()) {
            throw new CustomException(ErrorCode.SEAT_CONFLICT);
        }

        ShowTime showTime = showTimeRepository.findById(bookingRequest.getShowTimeId()).orElseThrow(() -> new CustomException(ErrorCode.NO_DATA_IN_DATABASE));

        Booking booking = new Booking();
        booking.setUser(userRepository.findById(bookingRequest.getUserId()).orElseThrow(()-> new CustomException(ErrorCode.NO_DATA_IN_DATABASE)));
        booking.setShowTime(showTime);
        booking.setStatus(Booking.BookingStatus.PENDING);

        List<Ticket> tickets = selectedSeats.stream()
                .map(seat -> new Ticket(booking, seat, ticketService.caculatePrice(seat, showTime)))
                .collect(Collectors.toList());
        booking.setTickets(tickets);

        double totalPrice = tickets.stream().mapToDouble(ticket -> ticket.getPrice()).sum();

        double discountAmount = bookingRequest.getDiscountCode() == "" ? 0 : discountService.getDiscountByCode(bookingRequest.getDiscountCode()).getDiscountAmount();

        booking.setTotalPrice(totalPrice - ((totalPrice / 100) * discountAmount));

        Booking savedBooking = bookingReponsitory.save(booking);

        paymentService.processPayment(booking, bookingRequest.getPaymentMethod());

        return convertToBookingResponse(savedBooking);
    }

    public void deleteBooking(long booking_id) {
        bookingReponsitory.deleteById(booking_id);
    }

    public BookingResponse confirmBooking(long booking_id) {
        Booking booking = bookingReponsitory.findById(booking_id).orElseThrow(() -> new CustomException("Không tìm thấy đơn đặt vé",ErrorCode.NO_DATA_IN_DATABASE));
        booking.setStatus(Booking.BookingStatus.CONFIRMED);

        emailService.sendBookingConfirmationEmail(booking.getUser(), booking);

        return convertToBookingResponse(bookingReponsitory.save(booking));
    }

    public BookingResponse cancelBooking(long booking_id) {
        Booking booking = bookingReponsitory.findById(booking_id).orElseThrow(() -> new CustomException("Không tìm thấy đơn đặt vé",ErrorCode.NO_DATA_IN_DATABASE));
        booking.setStatus(Booking.BookingStatus.CANCELLED);

        ticketService.deleteListTickets(booking.getTickets());

        return convertToBookingResponse(bookingReponsitory.save(booking));
    }


    public BookingResponse convertToBookingResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .bookingDate(booking.getBookingDate())
                .bookingTime(booking.getBookingTime())
                .user(userService.convertToUserResponse(booking.getUser()))
                .tickets(booking.getTickets().stream().map(tickets -> ticketService.convertToTicketResponse(tickets)).collect(Collectors.toList()))
                .status(booking.getStatus())
                .totalPrice(booking.getTotalPrice())
                .build();
    }
}
