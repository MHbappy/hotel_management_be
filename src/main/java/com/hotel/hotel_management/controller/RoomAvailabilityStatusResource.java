package com.hotel.hotel_management.controller;

import com.hotel.hotel_management.model.RoomAvailabilityStatus;
import com.hotel.hotel_management.repository.RoomAvailabilityStatusRepository;
import com.hotel.hotel_management.service.RoomAvailabilityStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
public class RoomAvailabilityStatusResource {

    private final Logger log = LoggerFactory.getLogger(RoomAvailabilityStatusResource.class);

    private final RoomAvailabilityStatusService roomAvailabilityStatusService;

    private final RoomAvailabilityStatusRepository roomAvailabilityStatusRepository;

    public RoomAvailabilityStatusResource(
        RoomAvailabilityStatusService roomAvailabilityStatusService,
        RoomAvailabilityStatusRepository roomAvailabilityStatusRepository
    ) {
        this.roomAvailabilityStatusService = roomAvailabilityStatusService;
        this.roomAvailabilityStatusRepository = roomAvailabilityStatusRepository;
    }

    @PostMapping("/room-availability-statuses")
    public ResponseEntity<RoomAvailabilityStatus> createRoomAvailabilityStatus(@RequestBody RoomAvailabilityStatus roomAvailabilityStatus)
        throws URISyntaxException {
        log.debug("REST request to save RoomAvailabilityStatus : {}", roomAvailabilityStatus);
        if (roomAvailabilityStatus.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new roomAvailabilityStatus cannot already have an ID");
        }
        RoomAvailabilityStatus result = roomAvailabilityStatusService.save(roomAvailabilityStatus);
        return ResponseEntity
            .created(new URI("/api/room-availability-statuses/" + result.getId()))
            .body(result);
    }

    @PutMapping("/room-availability-statuses/{id}")
    public ResponseEntity<RoomAvailabilityStatus> updateRoomAvailabilityStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoomAvailabilityStatus roomAvailabilityStatus
    ) throws URISyntaxException {
        log.debug("REST request to update RoomAvailabilityStatus : {}, {}", id, roomAvailabilityStatus);
        if (roomAvailabilityStatus.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!Objects.equals(id, roomAvailabilityStatus.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID");
        }

        if (!roomAvailabilityStatusRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }

        RoomAvailabilityStatus result = roomAvailabilityStatusService.update(roomAvailabilityStatus);
        return ResponseEntity
            .ok()
            .body(result);
    }


    @GetMapping("/room-availability-statuses")
    public List<RoomAvailabilityStatus> getAllRoomAvailabilityStatuses() {
        log.debug("REST request to get all RoomAvailabilityStatuses");
        return roomAvailabilityStatusService.findAll();
    }

    @GetMapping("/room-availability-statuses/{id}")
    public ResponseEntity<RoomAvailabilityStatus> getRoomAvailabilityStatus(@PathVariable Long id) {
        log.debug("REST request to get RoomAvailabilityStatus : {}", id);
        Optional<RoomAvailabilityStatus> roomAvailabilityStatus = roomAvailabilityStatusService.findOne(id);
        return ResponseEntity.ok(roomAvailabilityStatus.get());
    }

    @DeleteMapping("/room-availability-statuses/{id}")
    public ResponseEntity<Void> deleteRoomAvailabilityStatus(@PathVariable Long id) {
        log.debug("REST request to delete RoomAvailabilityStatus : {}", id);
        roomAvailabilityStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
