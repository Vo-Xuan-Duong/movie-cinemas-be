package com.example.movie_cinemas_be.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //user id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user ;
    //seat id
    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "booking_id" )
    private Booking booking;

    private double price;

    private LocalDate ticketDate = LocalDate.now();

    public Ticket(Booking booking,Seat seat, double price) {
        this.booking = booking;
        this.seat = seat;
        this.user = booking.getUser();
        this.price = price;
    }

}
