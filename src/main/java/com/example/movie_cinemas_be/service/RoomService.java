package com.example.movie_cinemas_be.service;

import com.example.movie_cinemas_be.dtos.request.RoomRequest;
import com.example.movie_cinemas_be.dtos.request.SeatRequest;
import com.example.movie_cinemas_be.dtos.response.RoomResponse;
import com.example.movie_cinemas_be.dtos.response.SeatResponse;
import com.example.movie_cinemas_be.entitys.Cinema;
import com.example.movie_cinemas_be.entitys.Room;
import com.example.movie_cinemas_be.entitys.Seat;
import com.example.movie_cinemas_be.entitys.ShowTime;
import com.example.movie_cinemas_be.exception.CustomException;
import com.example.movie_cinemas_be.exception.ErrorCode;
import com.example.movie_cinemas_be.reponsitory.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoomService {
    private final ShowTimeRepository showTimeRepository;
    private List<String> row_Seat = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M"));

    private RoomRepository roomRepository;
    private SeatRepository seatRepository;
    private CinemaRepository cinemaRepository;
    private TicketRepository ticketRepository;

    public RoomService(RoomRepository roomRepository,
                       SeatRepository seatRepository,
                       CinemaRepository cinemaRepository,
                       TicketRepository ticketRepository, ShowTimeRepository showTimeRepository) {
        this.roomRepository = roomRepository;
        this.seatRepository = seatRepository;
        this.cinemaRepository = cinemaRepository;
        this.ticketRepository = ticketRepository;
        this.showTimeRepository = showTimeRepository;
    }

    public Page<RoomResponse> getAllRoomByCinema(long cinemaId, Pageable  pageable) {

        Page<Room> rooms = roomRepository.findAllByCinema_Id(cinemaId, pageable);
        if (rooms.isEmpty()) {
            throw new CustomException(ErrorCode.NO_DATA_IN_DATABASE);
        }
        return rooms.map(room -> convertToRoomResponse(room));
    }

    public List<RoomResponse> getAllRoomByCinema(long cinemaId) {
        List<Room> rooms = roomRepository.findAllByCinema_Id(cinemaId);
        if (rooms.isEmpty()) {
            throw new CustomException(ErrorCode.NO_DATA_IN_DATABASE);
        }
        return rooms.stream().map(room -> convertToRoomResponse(room)).collect(Collectors.toList());
    }

    public  Page<RoomResponse> getAllRooms(Pageable pageable) {
        Page<Room> rooms = roomRepository.findAll(pageable);
        if (rooms.isEmpty()) {
            throw new CustomException(ErrorCode.NO_DATA_IN_DATABASE);

        }
        return rooms.map(room -> convertToRoomResponse(room));
    }


    public RoomResponse addRoom(RoomRequest roomRequest, long cinemaId) {
        Room newRoom = Room.builder()
                .name(roomRequest.getRoom_name())
                .capacity(roomRequest.getSeat_quantity())
                .roomType(roomRequest.getRoom_type())
                .creationDate(LocalDate.now())
                .cinema(cinemaRepository.findById(cinemaId).get())
                .build();
        Room room = roomRepository.save(newRoom);
        dynamicAddSeatFromRoom(room, room.getCapacity());
        return convertToRoomResponse(room);
    }

    public RoomResponse updateRoom(RoomRequest roomRequest, long roomId) {
        Room newRoom = roomRepository.findById(roomId).get();
        newRoom.setName(roomRequest.getRoom_name());
        if (roomRequest.getSeat_quantity() != newRoom.getCapacity()) {
            newRoom.setCapacity(roomRequest.getSeat_quantity());
        }
        newRoom.setRoomType(roomRequest.getRoom_type());
        newRoom.setCreationDate(LocalDate.now());
        Room room = roomRepository.save(newRoom);
        dynamicRemoveAllSeatFromRoom(room.getId());
        dynamicAddSeatFromRoom(room, room.getCapacity());
        return convertToRoomResponse(room);
    }

    public void deleteRoom(long room_id) {
        dynamicRemoveAllSeatFromRoom(room_id);
        roomRepository.deleteById(room_id);
    }

    public void deleteAllRoomsByCinema(long cinemaId) {
        List<Room> rooms = roomRepository.findAllByCinema_Id(cinemaId);
        for (Room room : rooms) {
            dynamicRemoveAllSeatFromRoom(room.getId());
            roomRepository.delete(room);
        }
    }

    public void dynamicAddSeatFromRoom(Room room, int numberSeat){
        //Mặc định mỗi hàng 12 ghế  sau này nâng cấp sau
        int count_seat = 0;
        for( int i = 0; i < row_Seat.size(); i++){
            String rowSeat = row_Seat.get(i);
            for( int j = 1; j <= 12; j++){
                count_seat++;
                if(count_seat <= numberSeat){
                    Seat seat = new Seat();
                    seat.setRoom(room);
                    seat.setSeatNumber(rowSeat + j);
                    seatRepository.save(seat);
                }else{
                    break;
                }
            }
        }
    }

    public List<SeatResponse> getAllSeatByRoom(long room_id) {
        List<Seat> seats = seatRepository.findSeatsByRoom(room_id);
        return  seats.stream().map(seat -> convertToSeatResponse(seat)).collect(Collectors.toList());
    }

    public void dynamicRemoveAllSeatFromRoom(long roomId){
        Room room = roomRepository.findById(roomId).get();
        List<Seat> seats = seatRepository.findByRoom(room);
        for( Seat seat : seats){
            seatRepository.delete(seat);
        }
    }

    public List<SeatResponse>  getListOfSeats(long roomId){
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new CustomException( "Room not found",ErrorCode.NO_DATA_IN_DATABASE));
        List<Seat> seats = seatRepository.findByRoom(room);
        return seats.stream().map(seat -> convertToSeatResponse(seat)).collect(Collectors.toList());
    }

    public SeatResponse deleteSeatById(long seat_id){
        Seat seat = seatRepository.findById(seat_id).get();
        seatRepository.deleteById(seat_id);
        Seat seatNew = new Seat();
        seatNew.setRoom(seat.getRoom());
       return convertToSeatResponse(seatNew);
    }

    public SeatResponse updateSeatType(long seat_id, Seat.SeatType type){
        Seat seat = seatRepository.findById(seat_id).get();
        seat.setSeatType(type);
        return convertToSeatResponse(seatRepository.save(seat));
    }

    public SeatResponse createSeatByRoomId(long room_id, SeatRequest seatRequest){
        Seat seat = new Seat();
        seat.setRoom(roomRepository.findById(room_id).get());
        seat.setSeatNumber(seatRequest.getSeatNumber());
        seat.setSeatType(seatRequest.getSeatType());
        return convertToSeatResponse(seatRepository.save(seat));
    }




    public List<SeatResponse> getAvailableSeats(long showTimeId){

        List<Long> bookedSeatIds = ticketRepository.findBookedSeatIdsByShowTime(showTimeId);

        ShowTime showTime = showTimeRepository.findById(showTimeId).get();

        List<Seat> allSeats = seatRepository.findSeatsByRoom(showTime.getRoom().getId());

        return allSeats.stream()
                .filter(seat -> !bookedSeatIds.contains(seat.getId()))
                .map(seat -> convertToSeatResponse(seat))
                .collect(Collectors.toList());
    }

    public List<SeatResponse> getAllSeatToShowTime(long showTimeId){
        List<Long> bookedSeatIds = ticketRepository.findBookedSeatIdsByShowTime(showTimeId);

        ShowTime showTime = showTimeRepository.findById(showTimeId).get();

        List<Seat> allSeats = seatRepository.findSeatsByRoom(showTime.getRoom().getId());

        return allSeats.stream()
                .map(seat -> {
            if(!bookedSeatIds.contains(seat.getId())){
                return convertToSeatResponse1(seat, Seat.SeatStatus.AVAILABLE);

            }else{
                return convertToSeatResponse1(seat, Seat.SeatStatus.OCCUPIED);
            }
        }).collect(Collectors.toList());

    }









    public SeatResponse convertToSeatResponse(Seat seat){
        return SeatResponse.builder()
                .seatNumber(seat.getSeatNumber())
                .id(seat.getId())
                .roomId(seat.getRoom().getId())
                .seatType(seat.getSeatType())
                .build();
    }

    public SeatResponse convertToSeatResponse1(Seat seat, Seat.SeatStatus seatStatus){
        return SeatResponse.builder()
                .seatNumber(seat.getSeatNumber())
                .id(seat.getId())
                .roomId(seat.getRoom().getId())
                .seatType(seat.getSeatType())
                .seatStatus(seatStatus)
                .build();
    }

    public RoomResponse convertToRoomResponse(Room room){
        return RoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .capacity(room.getCapacity())
                .type(room.getRoomType())
                .date(room.getCreationDate())
                .build();
    }
}
