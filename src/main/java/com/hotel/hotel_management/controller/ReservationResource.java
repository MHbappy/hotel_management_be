package com.hotel.hotel_management.controller;

import com.hotel.hotel_management.configuration.Constrains;
import com.hotel.hotel_management.dto.ReservationDTO;
import com.hotel.hotel_management.dto.ReservationUpdateDTO;
import com.hotel.hotel_management.enumuration.ReservationStatus;
import com.hotel.hotel_management.model.*;
import com.hotel.hotel_management.repository.*;
import com.hotel.hotel_management.security.SecurityUtils;
import com.hotel.hotel_management.service.ReservationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReservationResource {

    private final Logger log = LoggerFactory.getLogger(ReservationResource.class);
    private final ReservationService reservationService;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final RoomAvailabilityStatusRepository roomAvailabilityStatusRepository;
    private final ReservationRepository reservationRepository;
    private final CreditCardRepository creditCardRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentStatusRepository paymentStatusRepository;
    private final ModelMapper modelMapper;

    //Add payment
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDTO reservation) throws URISyntaxException {
        log.debug("REST request to save Reservation : {}", reservation);
        Reservation newReservation = modelMapper.map(reservation, Reservation.class);

        Boolean isGuest = SecurityUtils.hasCurrentUserThisAuthority(Constrains.guest);
        if (isGuest){
            String userName = SecurityUtils.getCurrentUserLogin().get();
            Users user = userRepository.findByEmail(userName);
            newReservation.setUsers(user);
        }else {
            if (reservation.getUserId() == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please add a user");
            }
        }

        //Change room status
        if (reservation.getRoomId() != null){
            Room room = roomRepository.findById(reservation.getRoomId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room id invalid!"));
            if (room.getRoomAvailabilityStatus().getName().equals(Constrains.roomAvailabilityStatusReserved)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room already reserved, please checkout the guest first!");
            }
            if (reservation.getNumberOfGuests() > room.getMaxGuests()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room maximum guest exceed!");
            }
            RoomAvailabilityStatus roomAvailabilityStatus = roomAvailabilityStatusRepository.findByName(Constrains.roomAvailabilityStatusReserved);
            room.setRoomAvailabilityStatus(roomAvailabilityStatus);
            roomRepository.save(room);
        }
        newReservation.setReservationStatus(ReservationStatus.RESERVED);
        Reservation result = reservationService.save(newReservation);
        //Add Payment
        if (reservation.getCardId() != null){
            Payment payment = new Payment();
            CreditCard creditCard = creditCardRepository.findById(reservation.getCardId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credit card id invalid!"));
            PaymentStatus paymentStatus = paymentStatusRepository.findByName(Constrains.paymentStatusPaid);
            payment.setReservation(result);
            payment.setPaymentStatus(paymentStatus);
            payment.setUsers(newReservation.getUsers());
            payment.setCreditCard(creditCard);
            payment.setPaymentDateTime(LocalDateTime.now());
            payment.setAmount(reservation.getPrice());
            paymentRepository.save(payment);
        }
        return ResponseEntity
            .created(new URI("/api/reservations/" + result.getId()))
            .body(result);
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity<Reservation> updateReservation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReservationUpdateDTO reservation
    ) throws URISyntaxException {
        log.debug("REST request to update Reservation : {}, {}", id, reservation);
        if (reservation.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!Objects.equals(id, reservation.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }

        if (!reservationRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        Reservation updatedReservation = modelMapper.map(reservation, Reservation.class);
        Reservation result = reservationService.update(updatedReservation);
        return ResponseEntity
            .ok()
            .body(result);
    }

    @GetMapping("/reservations")
    public List<Reservation> getAllReservations() {
        log.debug("REST request to get all Reservations");
        return reservationService.findAll();
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable Long id) {
        log.debug("REST request to get Reservation : {}", id);
        Optional<Reservation> reservation = reservationService.findOne(id);
        return ResponseEntity.ok(reservation.get());
    }

//    @DeleteMapping("/reservations/{id}")
//    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
//        log.debug("REST request to delete Reservation : {}", id);
//        reservationService.delete(id);
//        return ResponseEntity
//            .noContent()
//            .build();
//    }
}
