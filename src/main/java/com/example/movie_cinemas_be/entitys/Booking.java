package com.example.movie_cinemas_be.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "showtime_id", nullable = false)
    private ShowTime showTime;

    //List  ticket
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();
    //food

    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.PENDING;

    private LocalDate bookingDate = LocalDate.now();
    private LocalTime bookingTime = LocalTime.now();

    public enum BookingStatus {
        PENDING, CONFIRMED, CANCELLED
    }
}
