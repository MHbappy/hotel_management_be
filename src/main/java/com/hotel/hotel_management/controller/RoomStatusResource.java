package com.hotel.hotel_management.controller;

import com.hotel.hotel_management.model.RoomStatus;
import com.hotel.hotel_management.service.RoomStatusService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RoomStatusResource {

    private final Logger log = LoggerFactory.getLogger(RoomStatusResource.class);
    private final RoomStatusService roomStatusService;

    @GetMapping("/room-statuses")
    public List<RoomStatus> getAllRoomStatuses() {
        log.debug("REST request to get all RoomStatuses");
        return roomStatusService.findAll();
    }
}
