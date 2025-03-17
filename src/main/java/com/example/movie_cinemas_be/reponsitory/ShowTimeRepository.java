package com.example.movie_cinemas_be.reponsitory;

import com.example.movie_cinemas_be.entitys.Room;
import com.example.movie_cinemas_be.entitys.ShowTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {
    Page<ShowTime> findShowTimeByRoom_Id(long room_id, Pageable pageable);

    List<ShowTime> findAllByMovieId(long movie_id);

    Page<ShowTime> findAll(Pageable pageable);

    boolean existsByRoomAndShowDateAndStartTimeBetween(Room room, LocalDate showDate, LocalTime start, LocalTime end);

    boolean existsByRoomAndShowDateAndEndTimeBetween(Room room, LocalDate showDate, LocalTime start, LocalTime end);

    boolean existsByRoomAndShowDateAndStartTimeLessThanAndEndTimeGreaterThan(
            Room room, LocalDate showDate, LocalTime end, LocalTime start);

    default boolean existsByRoomAndTimeRange(Room room, LocalDate showDate, LocalTime start, LocalTime end) {
        return existsByRoomAndShowDateAndStartTimeBetween(room, showDate, start, end) ||
                existsByRoomAndShowDateAndEndTimeBetween(room, showDate, start, end) ||
                existsByRoomAndShowDateAndStartTimeLessThanAndEndTimeGreaterThan(room, showDate, end, start);
    }

    Page<ShowTime> findShowTimeByRoom_IdAndShowDate(long roomId, LocalDate showDate, Pageable pageable);

    Page<ShowTime> findShowTimeByShowDate(LocalDate showDate, Pageable pageable);
}
