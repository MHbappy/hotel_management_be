package com.hotel.hotel_management.service;

import com.hotel.hotel_management.model.RoomAvailabilityStatus;
import com.hotel.hotel_management.repository.RoomAvailabilityStatusRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomAvailabilityStatusService {

    private final Logger log = LoggerFactory.getLogger(RoomAvailabilityStatusService.class);
    private final RoomAvailabilityStatusRepository roomAvailabilityStatusRepository;
    
    @Transactional(readOnly = true)
    public List<RoomAvailabilityStatus> findAll() {
        log.debug("Request to get all RoomAvailabilityStatuses");
        return roomAvailabilityStatusRepository.findAllByIsActiveTrue();
    }

}
