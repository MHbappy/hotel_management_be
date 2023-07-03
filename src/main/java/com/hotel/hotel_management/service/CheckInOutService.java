package com.hotel.hotel_management.service;

import com.hotel.hotel_management.model.CheckInOut;
import com.hotel.hotel_management.repository.CheckInOutRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CheckInOutService {

    private final Logger log = LoggerFactory.getLogger(CheckInOutService.class);

    private final CheckInOutRepository checkInOutRepository;

    public CheckInOutService(CheckInOutRepository checkInOutRepository) {
        this.checkInOutRepository = checkInOutRepository;
    }

    
    public CheckInOut save(CheckInOut checkInOut) {
        log.debug("Request to save CheckInOut : {}", checkInOut);
        return checkInOutRepository.save(checkInOut);
    }

    
    public CheckInOut update(CheckInOut checkInOut) {
        log.debug("Request to update CheckInOut : {}", checkInOut);
        return checkInOutRepository.save(checkInOut);
    }

    
    public Optional<CheckInOut> partialUpdate(CheckInOut checkInOut) {
        log.debug("Request to partially update CheckInOut : {}", checkInOut);

        return checkInOutRepository
            .findById(checkInOut.getId())
            .map(existingCheckInOut -> {
                if (checkInOut.getStartDateTime() != null) {
                    existingCheckInOut.setStartDateTime(checkInOut.getStartDateTime());
                }
                if (checkInOut.getEndDateTime() != null) {
                    existingCheckInOut.setEndDateTime(checkInOut.getEndDateTime());
                }

                return existingCheckInOut;
            })
            .map(checkInOutRepository::save);
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
