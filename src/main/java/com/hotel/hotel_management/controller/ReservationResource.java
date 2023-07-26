package com.hotel.hotel_management.controller;

import com.hotel.hotel_management.configuration.Constrains;
import com.hotel.hotel_management.dto.ReservationDTO;
import com.hotel.hotel_management.dto.ReservationUpdateDTO;
import com.hotel.hotel_management.enumuration.CheckInStatus;
import com.hotel.hotel_management.enumuration.ReservationStatus;
import com.hotel.hotel_management.model.*;
import com.hotel.hotel_management.repository.*;
import com.hotel.hotel_management.security.SecurityUtils;
import com.hotel.hotel_management.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReservationResource {

    private final Logger log = LoggerFactory.getLogger(ReservationResource.class);
    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;

    //Add payment
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody @Valid ReservationDTO reservation) throws URISyntaxException {
        Reservation result = reservationService.createReservation(reservation);
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

        Reservation updatedReservation = reservationRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id"));
        updatedReservation.setNumberOfGuests(reservation.getNumberOfGuests());
        updatedReservation.setReservationStatus(reservation.getReservationStatus());

        Reservation result = reservationService.update(updatedReservation);
        return ResponseEntity
            .ok()
            .body(result);
    }

    @GetMapping("/reservations")
    public Page<Reservation> getAllReservations(Pageable pageable) {
        log.debug("REST request to get all Reservations");
        return reservationService.findAll(pageable);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable Long id) {
        log.debug("REST request to get Reservation : {}", id);
        Optional<Reservation> reservation = reservationService.findOne(id);
        return ResponseEntity.ok(reservation.get());
    }

}
