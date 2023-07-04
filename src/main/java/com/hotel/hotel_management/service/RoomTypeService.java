package com.hotel.hotel_management.service;

import com.hotel.hotel_management.model.RoomType;
import com.hotel.hotel_management.repository.RoomTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoomTypeService {

    private final Logger log = LoggerFactory.getLogger(RoomTypeService.class);

    private final RoomTypeRepository roomTypeRepository;

    public RoomTypeService(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    
    public RoomType save(RoomType roomType) {
        log.debug("Request to save RoomType : {}", roomType);
        return roomTypeRepository.save(roomType);
    }

    public RoomType update(RoomType roomType) {
        log.debug("Request to update RoomType : {}", roomType);
        return roomTypeRepository.save(roomType);
    }
    
    @Transactional(readOnly = true)
    public List<RoomType> findAll() {
        log.debug("Request to get all RoomTypes");
        return roomTypeRepository.findAll();
    }

    
    @Transactional(readOnly = true)
    public Optional<RoomType> findOne(Long id) {
        log.debug("Request to get RoomType : {}", id);
        return roomTypeRepository.findById(id);
    }

    
    public void delete(Long id) {
        log.debug("Request to delete RoomType : {}", id);
        roomTypeRepository.deleteById(id);
    }
}
