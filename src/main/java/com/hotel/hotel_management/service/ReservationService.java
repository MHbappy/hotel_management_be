package com.hotel.hotel_management.service;

import com.hotel.hotel_management.configuration.Constrains;
import com.hotel.hotel_management.dto.ReservationDTO;
import com.hotel.hotel_management.enumuration.CheckInStatus;
import com.hotel.hotel_management.enumuration.ReservationStatus;
import com.hotel.hotel_management.model.*;
import com.hotel.hotel_management.repository.*;
import com.hotel.hotel_management.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final Logger log = LoggerFactory.getLogger(ReservationService.class);

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
//    private final ReservationService reservationService;
    private final RoomRepository roomRepository;
    private final CreditCardRepository creditCardRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentStatusRepository paymentStatusRepository;
    private final CheckInOutRepository checkInOutRepository;

    
    public Reservation save(Reservation reservation) {
        log.debug("Request to save Reservation : {}", reservation);
        return reservationRepository.save(reservation);
    }

    public Reservation createReservation(@RequestBody @Valid ReservationDTO reservation) throws URISyntaxException {
        log.debug("REST request to save Reservation : {}", reservation);

        Reservation newReservation  = new Reservation();
        newReservation.setStartDate(reservation.getStartDate());
        newReservation.setEndDate(reservation.getEndDate());
        newReservation.setNumberOfGuests(reservation.getNumberOfGuests());

        Boolean isGuest = SecurityUtils.hasCurrentUserThisAuthority(Constrains.guest);
        String userName = SecurityUtils.getCurrentUserLogin().get();
        Users user = userRepository.findByEmail(userName);
        if (isGuest){
            newReservation.setUsers(user);
        }else {
            newReservation.setUsers(new Users(reservation.getUserId()));
            if (reservation.getUserId() == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please add a user");
            }
        }

        //Change room status
        if (reservation.getRoomId() != null){
            Room room = roomRepository.findById(reservation.getRoomId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room id invalid!"));
            if (reservation.getNumberOfGuests() > room.getMaxGuests()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room maximum guest exceed!");
            }
            newReservation.setRoom(room);
        }
        newReservation.setReservationStatus(ReservationStatus.RESERVED);

        CheckInOut checkInOut = new CheckInOut();
        checkInOut.setCheckInStatus(CheckInStatus.CHECK_IN);
        CheckInOut savedCheckInOut = checkInOutRepository.save(checkInOut);
        newReservation.setCheckInOut(savedCheckInOut);
        Reservation result = reservationRepository.save(newReservation);

        //Checkin without date
        //Add Payment
        if (reservation.getCardId() != null){
            Payment payment = new Payment();
            CreditCard creditCard = creditCardRepository.findById(reservation.getCardId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credit card id invalid!"));
            PaymentStatus paymentStatus = paymentStatusRepository.findByName(Constrains.paymentStatusPaid);
            payment.setReservation(result);
            payment.setUsers(user);
            payment.setPaymentStatus(paymentStatus);
            payment.setUsers(newReservation.getUsers());
            payment.setCreditCard(creditCard);
            payment.setPaymentDateTime(LocalDateTime.now());
            payment.setAmount(reservation.getPrice());
            paymentRepository.save(payment);
        }
        return result;
    }

    
    public Reservation update(Reservation reservation) {
        log.debug("Request to update Reservation : {}", reservation);
        return reservationRepository.save(reservation);
    }
    
    @Transactional(readOnly = true)
    public Page<Reservation> findAll(Pageable pageable) {
        log.debug("Request to get all Reservations");
        Boolean isGuest = SecurityUtils.hasCurrentUserThisAuthority(Constrains.guest);
        if (isGuest){
            String userName = SecurityUtils.getCurrentUserLogin().get();
            Users user = userRepository.findByEmail(userName);
            return reservationRepository.findAllByUsers(user, pageable);
        }
        return reservationRepository.findAll(pageable);
    }
    
    @Transactional(readOnly = true)
    public Optional<Reservation> findOne(Long id) {
        log.debug("Request to get Reservation : {}", id);
        return reservationRepository.findById(id);
    }

    
    public void delete(Long id) {
        log.debug("Request to delete Reservation : {}", id);
        reservationRepository.deleteById(id);
    }
}
