package com.hotel.hotel_management.controller;

import com.hotel.hotel_management.model.RoomType;
import com.hotel.hotel_management.repository.RoomTypeRepository;
import com.hotel.hotel_management.service.RoomTypeService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class RoomTypeResource {

    private final Logger log = LoggerFactory.getLogger(RoomTypeResource.class);
    private final RoomTypeService roomTypeService;

    @GetMapping("/room-types")
    public List<RoomType> getAllRoomTypes() {
        log.debug("REST request to get all RoomTypes");
        return roomTypeService.findAll();
    }
}
