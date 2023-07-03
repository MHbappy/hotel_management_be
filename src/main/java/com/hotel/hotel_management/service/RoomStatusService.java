package com.hotel.hotel_management.service;

import com.hotel.hotel_management.model.RoomStatus;
import com.hotel.hotel_management.repository.RoomStatusRepository;
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
public class RoomStatusService {

    private final Logger log = LoggerFactory.getLogger(RoomStatusService.class);

    private final RoomStatusRepository roomStatusRepository;

    public RoomStatusService(RoomStatusRepository roomStatusRepository) {
        this.roomStatusRepository = roomStatusRepository;
    }

    
    public RoomStatus save(RoomStatus roomStatus) {
        log.debug("Request to save RoomStatus : {}", roomStatus);
        return roomStatusRepository.save(roomStatus);
    }

    
    public RoomStatus update(RoomStatus roomStatus) {
        log.debug("Request to update RoomStatus : {}", roomStatus);
        return roomStatusRepository.save(roomStatus);
    }

    
    public Optional<RoomStatus> partialUpdate(RoomStatus roomStatus) {
        log.debug("Request to partially update RoomStatus : {}", roomStatus);

        return roomStatusRepository
            .findById(roomStatus.getId())
            .map(existingRoomStatus -> {
                if (roomStatus.getName() != null) {
                    existingRoomStatus.setName(roomStatus.getName());
                }
                if (roomStatus.getIsActive() != null) {
                    existingRoomStatus.setIsActive(roomStatus.getIsActive());
                }

                return existingRoomStatus;
            })
            .map(roomStatusRepository::save);
    }

    
    @Transactional(readOnly = true)
    public List<RoomStatus> findAll() {
        log.debug("Request to get all RoomStatuses");
        return roomStatusRepository.findAll();
    }

    
    @Transactional(readOnly = true)
    public Optional<RoomStatus> findOne(Long id) {
        log.debug("Request to get RoomStatus : {}", id);
        return roomStatusRepository.findById(id);
    }

    
    public void delete(Long id) {
        log.debug("Request to delete RoomStatus : {}", id);
        roomStatusRepository.deleteById(id);
    }
}
