package com.hotel.hotel_management.service;

import com.hotel.hotel_management.model.Room;
import com.hotel.hotel_management.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    
    public Optional<Room> partialUpdate(Room room) {
        log.debug("Request to partially update Room : {}", room);

        return roomRepository
            .findById(room.getId())
            .map(existingRoom -> {
                if (room.getTitle() != null) {
                    existingRoom.setTitle(room.getTitle());
                }
                if (room.getRoomNumber() != null) {
                    existingRoom.setRoomNumber(room.getRoomNumber());
                }
                if (room.getFloot() != null) {
                    existingRoom.setFloot(room.getFloot());
                }
                if (room.getPricePerNight() != null) {
                    existingRoom.setPricePerNight(room.getPricePerNight());
                }
                if (room.getMaxGuests() != null) {
                    existingRoom.setMaxGuests(room.getMaxGuests());
                }
                if (room.getDescription() != null) {
                    existingRoom.setDescription(room.getDescription());
                }
                if (room.getImage() != null) {
                    existingRoom.setImage(room.getImage());
                }

                return existingRoom;
            })
            .map(roomRepository::save);
    }

    
    @Transactional(readOnly = true)
    public List<Room> findAll() {
        log.debug("Request to get all Rooms");
        return roomRepository.findAll();
    }

    
    @Transactional(readOnly = true)
    public Optional<Room> findOne(Long id) {
        log.debug("Request to get Room : {}", id);
        return roomRepository.findById(id);
    }

    
    public void delete(Long id) {
        log.debug("Request to delete Room : {}", id);
        roomRepository.deleteById(id);
    }
}
