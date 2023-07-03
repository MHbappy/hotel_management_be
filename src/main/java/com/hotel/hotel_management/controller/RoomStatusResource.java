package com.hotel.hotel_management.controller;

import com.hotel.hotel_management.model.RoomStatus;
import com.hotel.hotel_management.repository.RoomStatusRepository;
import com.hotel.hotel_management.service.RoomStatusService;
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
public class RoomStatusResource {

    private final Logger log = LoggerFactory.getLogger(RoomStatusResource.class);

    private final RoomStatusService roomStatusService;

    private final RoomStatusRepository roomStatusRepository;

    public RoomStatusResource(RoomStatusService roomStatusService, RoomStatusRepository roomStatusRepository) {
        this.roomStatusService = roomStatusService;
        this.roomStatusRepository = roomStatusRepository;
    }

    /**
     * {@code POST  /room-statuses} : Create a new roomStatus.
     *
     * @param roomStatus the roomStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roomStatus, or with status {@code 400 (Bad Request)} if the roomStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/room-statuses")
    public ResponseEntity<RoomStatus> createRoomStatus(@RequestBody RoomStatus roomStatus) throws URISyntaxException {
        log.debug("REST request to save RoomStatus : {}", roomStatus);
        if (roomStatus.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new roomStatus cannot already have an ID");
        }
        RoomStatus result = roomStatusService.save(roomStatus);
        return ResponseEntity
            .created(new URI("/api/room-statuses/" + result.getId()))
            .body(result);
    }

    @PutMapping("/room-statuses/{id}")
    public ResponseEntity<RoomStatus> updateRoomStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoomStatus roomStatus
    ) throws URISyntaxException {
        log.debug("REST request to update RoomStatus : {}, {}", id, roomStatus);
        if (roomStatus.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID");
        }
        if (!Objects.equals(id, roomStatus.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID");
        }

        if (!roomStatusRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }

        RoomStatus result = roomStatusService.update(roomStatus);
        return ResponseEntity
            .ok()
            .body(result);
    }


    @GetMapping("/room-statuses")
    public List<RoomStatus> getAllRoomStatuses() {
        log.debug("REST request to get all RoomStatuses");
        return roomStatusService.findAll();
    }

    @GetMapping("/room-statuses/{id}")
    public ResponseEntity<RoomStatus> getRoomStatus(@PathVariable Long id) {
        log.debug("REST request to get RoomStatus : {}", id);
        Optional<RoomStatus> roomStatus = roomStatusService.findOne(id);
        return ResponseEntity.ok(roomStatus.get());
    }

    @DeleteMapping("/room-statuses/{id}")
    public ResponseEntity<Void> deleteRoomStatus(@PathVariable Long id) {
        log.debug("REST request to delete RoomStatus : {}", id);
        roomStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
