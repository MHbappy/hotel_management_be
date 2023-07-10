package com.hotel.hotel_management.controller;

import com.hotel.hotel_management.configuration.Constrains;
import com.hotel.hotel_management.enumuration.ReservationStatus;
import com.hotel.hotel_management.model.Reservation;
import com.hotel.hotel_management.model.Room;
import com.hotel.hotel_management.model.RoomAvailabilityStatus;
import com.hotel.hotel_management.model.Users;
import com.hotel.hotel_management.repository.ReservationRepository;
import com.hotel.hotel_management.repository.RoomAvailabilityStatusRepository;
import com.hotel.hotel_management.repository.RoomRepository;
import com.hotel.hotel_management.repository.UserRepository;
import com.hotel.hotel_management.security.SecurityUtils;
import com.hotel.hotel_management.service.ReservationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;
import java.net.URISyntaxException;
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

//    public ReservationResource(ReservationService reservationService, ReservationRepository reservationRepository) {
//        this.reservationService = reservationService;
//        this.reservationRepository = reservationRepository;
//    }


    //Add payment
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) throws URISyntaxException {
        log.debug("REST request to save Reservation : {}", reservation);
        if (reservation.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new reservation cannot already have an ID");
        }

        Boolean isGuest = SecurityUtils.hasCurrentUserThisAuthority(Constrains.guest);
        if (isGuest){
            String userName = SecurityUtils.getCurrentUserLogin().get();
            Users user = userRepository.findByEmail(userName);
            reservation.setUsers(user);
        }else {
            if (reservation.getUsers() == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please add a user");
            }
        }

        //Change room status
        if (reservation.getRoom() != null){
            Room room = roomRepository.findById(reservation.getRoom().getId()).get();
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
        reservation.setReservationStatus(ReservationStatus.RESERVED);
        Reservation result = reservationService.save(reservation);
        return ResponseEntity
            .created(new URI("/api/reservations/" + result.getId()))
            .body(result);
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity<Reservation> updateReservation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Reservation reservation
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

        Reservation result = reservationService.update(reservation);
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

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        log.debug("REST request to delete Reservation : {}", id);
        reservationService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
