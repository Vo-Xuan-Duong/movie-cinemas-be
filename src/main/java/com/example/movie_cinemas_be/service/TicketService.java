package com.example.movie_cinemas_be.service;

import com.example.movie_cinemas_be.dtos.request.TicketRequest;
import com.example.movie_cinemas_be.dtos.response.TicketResponse;
import com.example.movie_cinemas_be.entitys.Seat;
import com.example.movie_cinemas_be.entitys.ShowTime;
import com.example.movie_cinemas_be.entitys.Ticket;
import com.example.movie_cinemas_be.exception.CustomException;
import com.example.movie_cinemas_be.exception.ErrorCode;
import com.example.movie_cinemas_be.reponsitory.SeatRepository;
import com.example.movie_cinemas_be.reponsitory.ShowTimeRepository;
import com.example.movie_cinemas_be.reponsitory.TicketRepository;
import com.example.movie_cinemas_be.reponsitory.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final ShowTimeRepository showTimeRepository;
    private final ShowTimeService showTimeService;
    private final SeatRepository seatRepository;

    public TicketService(
            TicketRepository ticketRepository,
            UserRepository userRepository,
            ShowTimeRepository showTimeRepository,
            SeatRepository seatRepository,
            ShowTimeService showTimeService
            ) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.showTimeRepository = showTimeRepository;
        this.seatRepository = seatRepository;
        this.showTimeService = showTimeService;
    }

    public TicketResponse getTicketById(long id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.TICKET_NOT_FOUND));
        return convertToTicketResponse(ticket);
    }

    public List<TicketResponse> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream().map(this::convertToTicketResponse).collect(Collectors.toList());
    }

    public TicketResponse createTicket(long userId , TicketRequest ticketRequest) {
        Ticket ticket = new Ticket();
        ticket.setUser(userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTS_EXCEPTION)));
        ticket.setSeat(seatRepository.findById(ticketRequest.getSeatId()).orElseThrow(() -> new CustomException("Seat not found", ErrorCode.NO_DATA_IN_DATABASE)));
        ticket.setTicketDate(LocalDate.now());

        return convertToTicketResponse(ticketRepository.save(ticket));
    }

    public void deleteTicket(long ticketId) {
        ticketRepository.deleteById(ticketId);
    }

    public void deleteListTickets(List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            ticketRepository.delete(ticket);
        }
    }



    public double caculatePrice(Seat seat, ShowTime showTime) {
        double ticketPrice = 0;

        ticketPrice += showTime.getPrice();

        //nếu ghế VIP cộng thêm 50K
        //nếu couple cộng thêm 100K

        if(seat.getSeatType() == Seat.SeatType.VIP){
            ticketPrice += 50000;
        }else if(seat.getSeatType() == Seat.SeatType.COUPLE){
            ticketPrice += 100000;
        }else if(seat.getSeatType() == Seat.SeatType.STANDARD){
            ticketPrice += 0;
        }

        return ticketPrice;
    }



    public TicketResponse convertToTicketResponse(Ticket ticket) {
        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.setId(ticket.getId());
        ticketResponse.setUsername(ticket.getUser().getUsername());
        ticketResponse.setSeatNumber(ticket.getSeat().getSeatNumber());
        ticketResponse.setShowTime(showTimeService.convertToShowTimeResponse(ticket.getBooking().getShowTime()));
        ticketResponse.setTicketDate(ticket.getTicketDate());
        return ticketResponse;
    }

}
