package com.hotel.hotel_management.controller;

import com.hotel.hotel_management.model.Reservation;
import com.hotel.hotel_management.repository.ReservationRepository;
import com.hotel.hotel_management.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ReservationResource {

    private final Logger log = LoggerFactory.getLogger(ReservationResource.class);

    private final ReservationService reservationService;

    private final ReservationRepository reservationRepository;

    public ReservationResource(ReservationService reservationService, ReservationRepository reservationRepository) {
        this.reservationService = reservationService;
        this.reservationRepository = reservationRepository;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) throws URISyntaxException {
        log.debug("REST request to save Reservation : {}", reservation);
        if (reservation.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new reservation cannot already have an ID");
        }
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
