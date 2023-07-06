package com.hotel.hotel_management.service;

import com.hotel.hotel_management.model.RoomStatus;
import com.hotel.hotel_management.repository.RoomStatusRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link RoomStatus}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class RoomStatusService {

    private final Logger log = LoggerFactory.getLogger(RoomStatusService.class);
    private final RoomStatusRepository roomStatusRepository;
    
    @Transactional(readOnly = true)
    public List<RoomStatus> findAll() {
        log.debug("Request to get all RoomStatuses");
        return roomStatusRepository.findAllByIsActiveTrue();
    }
}
