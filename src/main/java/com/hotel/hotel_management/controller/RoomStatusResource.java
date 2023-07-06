package com.hotel.hotel_management.controller;

import com.hotel.hotel_management.model.RoomStatus;
import com.hotel.hotel_management.repository.RoomStatusRepository;
import com.hotel.hotel_management.service.RoomStatusService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class RoomStatusResource {

    private final Logger log = LoggerFactory.getLogger(RoomStatusResource.class);

    private final RoomStatusService roomStatusService;
    private final RoomStatusRepository roomStatusRepository;

    @GetMapping("/room-statuses")
    public List<RoomStatus> getAllRoomStatuses() {
        log.debug("REST request to get all RoomStatuses");
        return roomStatusService.findAll();
    }
}
