package com.example.movie_cinemas_be.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String seatNumber;

    //room
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Enumerated(EnumType.STRING)
    private SeatType  seatType = SeatType.STANDARD;

    public enum SeatType {
        STANDARD, VIP, COUPLE
    }

    public enum SeatStatus{
        AVAILABLE,
        OCCUPIED,

    }
}



