package com.hotel.hotel_management.service;

import com.hotel.hotel_management.model.RoomAvailabilityStatus;
import com.hotel.hotel_management.repository.RoomAvailabilityStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoomAvailabilityStatusService {

    private final Logger log = LoggerFactory.getLogger(RoomAvailabilityStatusService.class);

    private final RoomAvailabilityStatusRepository roomAvailabilityStatusRepository;

    public RoomAvailabilityStatusService(RoomAvailabilityStatusRepository roomAvailabilityStatusRepository) {
        this.roomAvailabilityStatusRepository = roomAvailabilityStatusRepository;
    }

    
    public RoomAvailabilityStatus save(RoomAvailabilityStatus roomAvailabilityStatus) {
        log.debug("Request to save RoomAvailabilityStatus : {}", roomAvailabilityStatus);
        return roomAvailabilityStatusRepository.save(roomAvailabilityStatus);
    }

    
    public RoomAvailabilityStatus update(RoomAvailabilityStatus roomAvailabilityStatus) {
        log.debug("Request to update RoomAvailabilityStatus : {}", roomAvailabilityStatus);
        return roomAvailabilityStatusRepository.save(roomAvailabilityStatus);
    }

    
    public Optional<RoomAvailabilityStatus> partialUpdate(RoomAvailabilityStatus roomAvailabilityStatus) {
        log.debug("Request to partially update RoomAvailabilityStatus : {}", roomAvailabilityStatus);

        return roomAvailabilityStatusRepository
            .findById(roomAvailabilityStatus.getId())
            .map(existingRoomAvailabilityStatus -> {
                if (roomAvailabilityStatus.getName() != null) {
                    existingRoomAvailabilityStatus.setName(roomAvailabilityStatus.getName());
                }
                if (roomAvailabilityStatus.getIsActive() != null) {
                    existingRoomAvailabilityStatus.setIsActive(roomAvailabilityStatus.getIsActive());
                }

                return existingRoomAvailabilityStatus;
            })
            .map(roomAvailabilityStatusRepository::save);
    }

    
    @Transactional(readOnly = true)
    public List<RoomAvailabilityStatus> findAll() {
        log.debug("Request to get all RoomAvailabilityStatuses");
        return roomAvailabilityStatusRepository.findAll();
    }

    
    @Transactional(readOnly = true)
    public Optional<RoomAvailabilityStatus> findOne(Long id) {
        log.debug("Request to get RoomAvailabilityStatus : {}", id);
        return roomAvailabilityStatusRepository.findById(id);
    }

    
    public void delete(Long id) {
        log.debug("Request to delete RoomAvailabilityStatus : {}", id);
        roomAvailabilityStatusRepository.deleteById(id);
    }
}
