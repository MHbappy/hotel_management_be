package com.hotel.hotel_management.service;

import com.hotel.hotel_management.model.PaymentStatus;
import com.hotel.hotel_management.repository.PaymentStatusRepository;
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
public class PaymentStatusService {

    private final Logger log = LoggerFactory.getLogger(PaymentStatusService.class);
    private final PaymentStatusRepository paymentStatusRepository;
    
    @Transactional(readOnly = true)
    public List<PaymentStatus> findAll() {
        log.debug("Request to get all PaymentStatuses");
        return paymentStatusRepository.findAllByIsActiveTrue();
    }

}
