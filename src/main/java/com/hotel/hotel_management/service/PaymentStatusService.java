package com.hotel.hotel_management.service;

import com.hotel.hotel_management.model.PaymentStatus;
import com.hotel.hotel_management.repository.PaymentStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaymentStatusService {

    private final Logger log = LoggerFactory.getLogger(PaymentStatusService.class);

    private final PaymentStatusRepository paymentStatusRepository;

    public PaymentStatusService(PaymentStatusRepository paymentStatusRepository) {
        this.paymentStatusRepository = paymentStatusRepository;
    }

    
    public PaymentStatus save(PaymentStatus paymentStatus) {
        log.debug("Request to save PaymentStatus : {}", paymentStatus);
        return paymentStatusRepository.save(paymentStatus);
    }

    
    public PaymentStatus update(PaymentStatus paymentStatus) {
        log.debug("Request to update PaymentStatus : {}", paymentStatus);
        return paymentStatusRepository.save(paymentStatus);
    }
    
    @Transactional(readOnly = true)
    public List<PaymentStatus> findAll() {
        log.debug("Request to get all PaymentStatuses");
        return paymentStatusRepository.findAll();
    }

    
    @Transactional(readOnly = true)
    public Optional<PaymentStatus> findOne(Long id) {
        log.debug("Request to get PaymentStatus : {}", id);
        return paymentStatusRepository.findById(id);
    }

    
    public void delete(Long id) {
        log.debug("Request to delete PaymentStatus : {}", id);
        paymentStatusRepository.deleteById(id);
    }
}
