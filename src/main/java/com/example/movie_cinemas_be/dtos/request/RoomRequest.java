package com.example.movie_cinemas_be.dtos.request;

import com.example.movie_cinemas_be.entitys.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest {
    private String room_name;
    private Room.RoomType room_type;
    private int seat_quantity;
}
