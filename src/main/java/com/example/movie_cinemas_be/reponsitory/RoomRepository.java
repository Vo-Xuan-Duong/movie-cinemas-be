package com.example.movie_cinemas_be.reponsitory;

import com.example.movie_cinemas_be.entitys.Cinema;
import com.example.movie_cinemas_be.entitys.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByCinema(Cinema cinema);
}
