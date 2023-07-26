package com.hotel.hotel_management.controller;

import com.hotel.hotel_management.configuration.Constrains;
import com.hotel.hotel_management.enumuration.CheckInStatus;
import com.hotel.hotel_management.model.CheckInOut;
import com.hotel.hotel_management.model.Room;
import com.hotel.hotel_management.model.RoomAvailabilityStatus;
import com.hotel.hotel_management.repository.CheckInOutRepository;
import com.hotel.hotel_management.repository.RoomAvailabilityStatusRepository;
import com.hotel.hotel_management.repository.RoomRepository;
import com.hotel.hotel_management.service.CheckInOutService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CheckInOutResource {
    private final CheckInOutRepository checkInOutRepository;

    @PostMapping("/checkin/{checkInOutId}")
    public ResponseEntity<CheckInOut> createCheckIn(@PathVariable Long checkInOutId) {
        CheckInOut checkInOutcheckInOut = checkInOutRepository.findById(checkInOutId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "checkinout not found!"));
        checkInOutcheckInOut.setCheckInStatus(CheckInStatus.CHECK_IN);
        checkInOutcheckInOut.setStartDateTime(LocalDateTime.now());
        CheckInOut checkInOut = checkInOutRepository.save(checkInOutcheckInOut);
        return ResponseEntity.ok(checkInOut);
    }

    @PostMapping("/checkout/{checkInOutId}")
    public ResponseEntity<CheckInOut> createCheckOut(@PathVariable Long checkInOutId) {
        CheckInOut checkInOutcheckInOut = checkInOutRepository.findById(checkInOutId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "checkinout not found!"));
        checkInOutcheckInOut.setCheckInStatus(CheckInStatus.CHECK_OUT);
        checkInOutcheckInOut.setEndDateTime(LocalDateTime.now());
        CheckInOut checkInOut = checkInOutRepository.save(checkInOutcheckInOut);
        return ResponseEntity.ok(checkInOut);
    }

}
