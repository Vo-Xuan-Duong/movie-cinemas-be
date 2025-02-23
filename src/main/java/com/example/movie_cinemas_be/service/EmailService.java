package com.example.movie_cinemas_be.service;

import com.example.movie_cinemas_be.entitys.Booking;
import com.example.movie_cinemas_be.entitys.Seat;
import com.example.movie_cinemas_be.entitys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendBookingConfirmationEmail(User user, Booking booking) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(user.getEmail());
            message.setSubject("Xác nhận đặt vé xem phim \uD83C\uDFAC");
            message.setText("Cảm ơn bạn đã đặt vé! 🎟️ \n"
                    + "Phim: " + booking.getShowTime().getMovie().getTitle() + "\n"
                    + "Suất chiếu: " + booking.getShowTime().getStartTime() + "\n"
                    + "Ghế: " + booking.getTickets().stream().map(ticket -> ticket.getSeat().getSeatNumber()).collect(Collectors.joining(", ")) + "\n");
//                    + "Vé của bạn: " + generateQRCode(booking.getId()));

            mailSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
