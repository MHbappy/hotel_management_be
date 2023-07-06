package com.hotel.hotel_management.controller;

import com.hotel.hotel_management.model.RoomAvailabilityStatus;
import com.hotel.hotel_management.repository.RoomAvailabilityStatusRepository;
import com.hotel.hotel_management.service.RoomAvailabilityStatusService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class RoomAvailabilityStatusResource {
    private final Logger log = LoggerFactory.getLogger(RoomAvailabilityStatusResource.class);
    private final RoomAvailabilityStatusService roomAvailabilityStatusService;

    @GetMapping("/room-availability-statuses")
    public List<RoomAvailabilityStatus> getAllRoomAvailabilityStatuses() {
        log.debug("REST request to get all RoomAvailabilityStatuses");
        return roomAvailabilityStatusService.findAll();
    }
}
