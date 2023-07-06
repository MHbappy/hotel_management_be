package com.hotel.hotel_management.service;

import com.hotel.hotel_management.model.RoomType;
import com.hotel.hotel_management.repository.RoomTypeRepository;
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
public class RoomTypeService {
    private final Logger log = LoggerFactory.getLogger(RoomTypeService.class);
    private final RoomTypeRepository roomTypeRepository;

    @Transactional(readOnly = true)
    public List<RoomType> findAll() {
        log.debug("Request to get all RoomTypes");
        return roomTypeRepository.findAllByIsActiveTrue();
    }
}
