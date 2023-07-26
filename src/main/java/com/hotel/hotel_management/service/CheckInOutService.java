package com.hotel.hotel_management.service;

import com.hotel.hotel_management.model.CheckInOut;
import com.hotel.hotel_management.repository.CheckInOutRepository;
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
public class CheckInOutService {

    private final Logger log = LoggerFactory.getLogger(CheckInOutService.class);
    private final CheckInOutRepository checkInOutRepository;
    
    public CheckInOut save(CheckInOut checkInOut) {
        log.debug("Request to save CheckInOut : {}", checkInOut);
        return checkInOutRepository.save(checkInOut);
    }

    public CheckInOut update(CheckInOut checkInOut) {
        log.debug("Request to update CheckInOut : {}", checkInOut);
        return checkInOutRepository.save(checkInOut);
    }

    @Transactional(readOnly = true)
    public List<CheckInOut> findAll() {
        log.debug("Request to get all CheckInOuts");
        return checkInOutRepository.findAll();
    }

    
    @Transactional(readOnly = true)
    public Optional<CheckInOut> findOne(Long id) {
        log.debug("Request to get CheckInOut : {}", id);
        return checkInOutRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete CheckInOut : {}", id);
        checkInOutRepository.deleteById(id);
    }
}
