package com.example.movie_cinemas_be.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest {
    private String room_name;
    private String room_type;
    private int seat_quantity;
}
