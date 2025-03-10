package com.example.movie_cinemas_be.service;

import com.example.movie_cinemas_be.dtos.request.BookingRequest;
import com.example.movie_cinemas_be.dtos.response.BookingResponse;
import com.example.movie_cinemas_be.dtos.response.DiscountResponse;
import com.example.movie_cinemas_be.entitys.*;
import com.example.movie_cinemas_be.exception.CustomException;
import com.example.movie_cinemas_be.exception.ErrorCode;
import com.example.movie_cinemas_be.reponsitory.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookingService {

    private final BookingReponsitory bookingReponsitory;
    private final PaymentService paymentService;
    private final SeatRepository seatRepository;
    private final ShowTimeRepository showTimeRepository;
    private final DiscountService discountService;
    private final EmailService emailService;
    private final DiscountRepository discountRepository;
    private UserService userService;
    private TicketService ticketService;
    private UserRepository userRepository;
    private QRCodeService qrCodeService;

    public BookingService(BookingReponsitory bookingReponsitory,
                          UserService userService,
                          TicketService ticketService,
                          PaymentService paymentService,
                          SeatRepository seatRepository,
                          ShowTimeRepository showTimeRepository,
                          DiscountService discountService,
                          EmailService emailService,
                          UserRepository userRepository,
                          QRCodeService qrCodeService, DiscountRepository discountRepository) {
        this.bookingReponsitory = bookingReponsitory;
        this.userService = userService;
        this.ticketService = ticketService;
        this.paymentService = paymentService;
        this.seatRepository = seatRepository;
        this.showTimeRepository = showTimeRepository;
        this.discountService = discountService;
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.qrCodeService = qrCodeService;
        this.discountRepository = discountRepository;
    }

    public Page<BookingResponse> getAllBookings(Pageable  pageable) {
        Page<Booking> bookings = bookingReponsitory.findAll(pageable);
        return  bookings.map(this::convertToBookingResponse);
    }

    public BookingResponse getBookingById(Long id) {
        return convertToBookingResponse(bookingReponsitory.findById(id)
                .orElseThrow(() -> new CustomException("Booking not found", ErrorCode.NO_DATA_IN_DATABASE)));
    }

    public BookingResponse createBooking(BookingRequest bookingRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//        String username = authentication.getName();
        String username = "voxuanduong02";

        List<Seat> selectedSeats = seatRepository.findByIdIn(bookingRequest.getSeatIds());
        if(selectedSeats.size() != bookingRequest.getSeatIds().size()) {
            throw new CustomException(ErrorCode.SEAT_CONFLICT);
        }

        ShowTime showTime = showTimeRepository.findById(bookingRequest.getShowTimeId()).orElseThrow(() -> new CustomException(ErrorCode.NO_DATA_IN_DATABASE));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShowTime(showTime);
        booking.setStatus(Booking.BookingStatus.PENDING);
        String bookingCode = bookingRequest.getPaymentMethod().name() + "VXD" + user.getId() +
                bookingRequest.getShowTimeId() +
                LocalDate.now().getYear() +
                new Random().nextInt(10000);
        booking.setBookingCode(bookingCode);
        List<Ticket> tickets = selectedSeats.stream()
                .map(seat -> new Ticket(booking, seat, ticketService.caculatePrice(seat, showTime)))
                .collect(Collectors.toList());
        booking.setTickets(tickets);

        double totalPrice = calculateTotalPrice(tickets, bookingRequest.getDiscountCode());
        booking.setTotalPrice(totalPrice);

        Booking savedBooking = bookingReponsitory.save(booking);

        paymentService.processPayment(booking, bookingRequest.getPaymentMethod());

        return convertToBookingResponse(savedBooking);
    }

    private double calculateTotalPrice(List<Ticket> tickets, String discountCode) {
        double totalPrice = tickets.stream().mapToDouble(Ticket::getPrice).sum();
        if (discountCode == null || discountCode.isEmpty()) {
            return totalPrice;
        }
        Discount discount = discountRepository.findDiscountByCode(discountCode);
        double quantityUsed = discount.getQuantity();
        if( quantityUsed > 0 ) {
            discount.setQuantity(quantityUsed - 1);
            Discount discountUp = discountRepository.save(discount);
            return discountUp.getDiscountAmount() == 0
                    ? totalPrice * (1 - discount.getDiscountRate() / 100)
                    : totalPrice - discount.getDiscountAmount();
        }
        return totalPrice;

    }

    public BookingResponse getBookingByBookingCode(String bookingCode) {
        return convertToBookingResponse(bookingReponsitory.findBookingByBookingCode(bookingCode));
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
                .bookingCode(booking.getBookingCode())
                .bookingDate(booking.getBookingDate())
                .bookingTime(booking.getBookingTime())
                .user(userService.convertToUserResponse(booking.getUser()))
                .tickets(booking.getTickets().stream().map(tickets -> ticketService.convertToTicketResponse(tickets)).collect(Collectors.toList()))
                .status(booking.getStatus())
                .qrCode(qrCodeService.generateQRCodeBase64(booking.getBookingCode(), 200, 200))
                .totalPrice(booking.getTotalPrice())
                .build();
    }
}
