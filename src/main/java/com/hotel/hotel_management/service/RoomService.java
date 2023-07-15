package com.hotel.hotel_management.service;

import com.hotel.hotel_management.model.Room;
import com.hotel.hotel_management.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Room}.
 */
@Service
@Transactional
public class RoomService {
    private final Logger log = LoggerFactory.getLogger(RoomService.class);
    private final RoomRepository roomRepository;
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room save(Room room) {
        log.debug("Request to save Room : {}", room);
        return roomRepository.save(room);
    }

    public Room update(Room room) {
        log.debug("Request to update Room : {}", room);
        return roomRepository.save(room);
    }
    
    @Transactional(readOnly = true)
    public List<Room> findAll() {
        log.debug("Request to get all Rooms");
        return roomRepository.findAllByIsActiveTrue();
    }

    @Transactional(readOnly = true)
    public List<Room> findAllActiveInactiveRoom() {
        log.debug("Request to get all Rooms");
        return roomRepository.findAllByIsActiveTrue();
    }

    
    @Transactional(readOnly = true)
    public Optional<Room> findOne(Long id) {
        log.debug("Request to get Room : {}", id);
        return roomRepository.findById(id);
    }

    
    public void delete(Long id) {
        log.debug("Request to delete Room : {}", id);
        Room room = roomRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room not found"));
        room.setIsActive(false);
        roomRepository.save(room);
    }
}
