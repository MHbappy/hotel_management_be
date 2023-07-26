package com.hotel.hotel_management.service;

import com.hotel.hotel_management.configuration.Constrains;
import com.hotel.hotel_management.model.Payment;
import com.hotel.hotel_management.model.Users;
import com.hotel.hotel_management.repository.PaymentRepository;
import com.hotel.hotel_management.repository.UserRepository;
import com.hotel.hotel_management.security.SecurityUtils;
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
public class PaymentService {

    private final Logger log = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    
    public Payment save(Payment payment) {
        log.debug("Request to save Payment : {}", payment);
        return paymentRepository.save(payment);
    }

    
    public Payment update(Payment payment) {
        log.debug("Request to update Payment : {}", payment);
        return paymentRepository.save(payment);
    }

    
    @Transactional(readOnly = true)
    public List<Payment> findAll(String email) {
        log.debug("Request to get all Payments");
        Boolean isGuest = SecurityUtils.hasCurrentUserThisAuthority(Constrains.guest);
        if (isGuest){
            String userName = SecurityUtils.getCurrentUserLogin().get();
            Users user = userRepository.findByEmail(userName);
            return paymentRepository.findAllByUsers(user);
        }
        if (email != null && !email.isEmpty()){
            return paymentRepository.findAllByUsersEmailLike("%" + email + "%");
        }
        return paymentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Payment> findOne(Long id) {
        log.debug("Request to get Payment : {}", id);
        return paymentRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Payment : {}", id);
        paymentRepository.deleteById(id);
    }
}
