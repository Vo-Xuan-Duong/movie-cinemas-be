package com.example.movie_cinemas_be.reponsitory;

import com.example.movie_cinemas_be.entitys.Cinema;
import com.example.movie_cinemas_be.entitys.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Page<Room> findAllByCinema_Id(long cinemaId, Pageable pageable);

    Page<Room> findAll(Pageable pageable);

    List<Room> findAllByCinema_Id(long cinemaId);
}
