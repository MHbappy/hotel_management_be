package com.hotel.hotel_management.controller;

import com.hotel.hotel_management.model.Room;
import com.hotel.hotel_management.repository.RoomRepository;
import com.hotel.hotel_management.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RoomResource {

    private final Logger log = LoggerFactory.getLogger(RoomResource.class);
    private final RoomService roomService;
    private final RoomRepository roomRepository;

    public RoomResource(RoomService roomService, RoomRepository roomRepository) {
        this.roomService = roomService;
        this.roomRepository = roomRepository;
    }

    @PostMapping("/rooms")
    public ResponseEntity<Room> createRoom(@RequestBody @Valid Room room) throws URISyntaxException {
        log.debug("REST request to save Room : {}", room);
        if (room.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new room cannot already have an ID");
        }
        Room result = roomService.save(room);
        return ResponseEntity
                .created(new URI("/api/rooms/" + result.getId()))
                .body(result);
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable(value = "id", required = false) final Long id, @RequestBody Room room)
            throws URISyntaxException {
        log.debug("REST request to update Room : {}, {}", id, room);
        if (room.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!Objects.equals(id, room.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }

        if (!roomRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }

        Room result = roomService.update(room);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/search-rooms")
    public Page<Room> getAllRooms(@RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestParam(value = "roomStatusId", required = false) Long roomStatusId, @RequestParam(value = "startPrice", required = false) Long startPrice, @RequestParam(value = "endPrice", required = false) Long endPrice, Pageable pageable) {
        log.debug("REST request to get all Rooms");
        if (date != null && roomStatusId != null && startPrice != null && endPrice != null) {
            return roomRepository.getRoomByRoomDateBetweenAndStatusIdAndPriceRange(date, roomStatusId, startPrice, endPrice, pageable);
        } else if (date != null && roomStatusId != null) {
            return roomRepository.getRoomByRoomDateBetweenAndStatusId(date, roomStatusId, pageable);
        }
        return roomRepository.getRoomByRoomDateBetween(date, pageable);
    }

    @GetMapping("/rooms")
    public Page<Room> getAllRooms(@RequestParam(value = "title", required = false) String title, Pageable pageable) {
        if (title == null) {
            return roomRepository.findAllByIsActiveTrue(pageable);
        }
        return roomRepository.findAllByTitleContainingIgnoreCaseAndIsActiveTrue(title, pageable);
    }


    @GetMapping("/rooms-without-pagination")
    public List<Room> getAllWithouPaginationRooms() {
        return roomRepository.findAllByIsActiveTrue();
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable Long id) {
        log.debug("REST request to get Room : {}", id);
        Optional<Room> room = roomService.findOne(id);
        return ResponseEntity.ok(room.get());
    }

    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        log.debug("REST request to delete Room : {}", id);
        roomService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
