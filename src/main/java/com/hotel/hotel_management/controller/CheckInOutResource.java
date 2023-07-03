package com.hotel.hotel_management.controller;

import com.hotel.hotel_management.model.CheckInOut;
import com.hotel.hotel_management.repository.CheckInOutRepository;
import com.hotel.hotel_management.service.CheckInOutService;
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
public class CheckInOutResource {

    private final Logger log = LoggerFactory.getLogger(CheckInOutResource.class);

    private final CheckInOutService checkInOutService;

    private final CheckInOutRepository checkInOutRepository;

    public CheckInOutResource(CheckInOutService checkInOutService, CheckInOutRepository checkInOutRepository) {
        this.checkInOutService = checkInOutService;
        this.checkInOutRepository = checkInOutRepository;
    }

    @PostMapping("/check-in-outs")
    public ResponseEntity<CheckInOut> createCheckInOut(@RequestBody CheckInOut checkInOut) throws URISyntaxException {
        log.debug("REST request to save CheckInOut : {}", checkInOut);
        if (checkInOut.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new checkInOut cannot already have an ID");
        }
        CheckInOut result = checkInOutService.save(checkInOut);
        return ResponseEntity
            .created(new URI("/api/check-in-outs/" + result.getId()))
            .body(result);
    }

    @PutMapping("/check-in-outs/{id}")
    public ResponseEntity<CheckInOut> updateCheckInOut(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CheckInOut checkInOut
    ) throws URISyntaxException {
        log.debug("REST request to update CheckInOut : {}, {}", id, checkInOut);
        if (checkInOut.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!Objects.equals(id, checkInOut.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID");
        }

        if (!checkInOutRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }

        CheckInOut result = checkInOutService.update(checkInOut);
        return ResponseEntity
            .ok()
            .body(result);
    }
    @GetMapping("/check-in-outs")
    public List<CheckInOut> getAllCheckInOuts() {
        log.debug("REST request to get all CheckInOuts");
        return checkInOutService.findAll();
    }

    @GetMapping("/check-in-outs/{id}")
    public ResponseEntity<CheckInOut> getCheckInOut(@PathVariable Long id) {
        log.debug("REST request to get CheckInOut : {}", id);
        Optional<CheckInOut> checkInOut = checkInOutService.findOne(id);
        return ResponseEntity.ok(checkInOut.get());
    }

    @DeleteMapping("/check-in-outs/{id}")
    public ResponseEntity<Void> deleteCheckInOut(@PathVariable Long id) {
        log.debug("REST request to delete CheckInOut : {}", id);
        checkInOutService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
