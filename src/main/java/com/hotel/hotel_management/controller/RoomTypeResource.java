package com.hotel.hotel_management.controller;

import com.hotel.hotel_management.model.RoomType;
import com.hotel.hotel_management.repository.RoomTypeRepository;
import com.hotel.hotel_management.service.RoomTypeService;
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
public class RoomTypeResource {

    private final Logger log = LoggerFactory.getLogger(RoomTypeResource.class);

    private final RoomTypeService roomTypeService;

    private final RoomTypeRepository roomTypeRepository;

    public RoomTypeResource(RoomTypeService roomTypeService, RoomTypeRepository roomTypeRepository) {
        this.roomTypeService = roomTypeService;
        this.roomTypeRepository = roomTypeRepository;
    }

    @PostMapping("/room-types")
    public ResponseEntity<RoomType> createRoomType(@RequestBody RoomType roomType) throws URISyntaxException {
        log.debug("REST request to save RoomType : {}", roomType);
        if (roomType.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new roomType cannot already have an ID");
        }
        RoomType result = roomTypeService.save(roomType);
        return ResponseEntity
            .created(new URI("/api/room-types/" + result.getId()))
            .body(result);
    }

    @PutMapping("/room-types/{id}")
    public ResponseEntity<RoomType> updateRoomType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoomType roomType
    ) throws URISyntaxException {
        log.debug("REST request to update RoomType : {}, {}", id, roomType);
        if (roomType.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!Objects.equals(id, roomType.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }

        if (!roomTypeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }

        RoomType result = roomTypeService.update(roomType);
        return ResponseEntity
            .ok()
            .body(result);
    }

    @GetMapping("/room-types")
    public List<RoomType> getAllRoomTypes() {
        log.debug("REST request to get all RoomTypes");
        return roomTypeService.findAll();
    }

    @GetMapping("/room-types/{id}")
    public ResponseEntity<RoomType> getRoomType(@PathVariable Long id) {
        log.debug("REST request to get RoomType : {}", id);
        Optional<RoomType> roomType = roomTypeService.findOne(id);
        return ResponseEntity.ok(roomType.get());
    }

    @DeleteMapping("/room-types/{id}")
    public ResponseEntity<Void> deleteRoomType(@PathVariable Long id) {
        log.debug("REST request to delete RoomType : {}", id);
        roomTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
