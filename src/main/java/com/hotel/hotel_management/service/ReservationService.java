package com.hotel.hotel_management.service;

import com.hotel.hotel_management.configuration.Constrains;
import com.hotel.hotel_management.model.Reservation;
import com.hotel.hotel_management.model.Users;
import com.hotel.hotel_management.repository.ReservationRepository;
import com.hotel.hotel_management.repository.UserRepository;
import com.hotel.hotel_management.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final Logger log = LoggerFactory.getLogger(ReservationService.class);

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    
    public Reservation save(Reservation reservation) {
        log.debug("Request to save Reservation : {}", reservation);
        return reservationRepository.save(reservation);
    }

    
    public Reservation update(Reservation reservation) {
        log.debug("Request to update Reservation : {}", reservation);
        return reservationRepository.save(reservation);
    }
    
    @Transactional(readOnly = true)
    public Page<Reservation> findAll(Pageable pageable) {
        log.debug("Request to get all Reservations");
        Boolean isGuest = SecurityUtils.hasCurrentUserThisAuthority(Constrains.guest);
        if (isGuest){
            String userName = SecurityUtils.getCurrentUserLogin().get();
            Users user = userRepository.findByEmail(userName);
            return reservationRepository.findAllByUsers(user, pageable);
        }
        return reservationRepository.findAll(pageable);
    }
    
    @Transactional(readOnly = true)
    public Optional<Reservation> findOne(Long id) {
        log.debug("Request to get Reservation : {}", id);
        return reservationRepository.findById(id);
    }

    
    public void delete(Long id) {
        log.debug("Request to delete Reservation : {}", id);
        reservationRepository.deleteById(id);
    }
}
