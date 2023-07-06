package com.hotel.hotel_management.controller;

import com.hotel.hotel_management.model.PaymentStatus;
import com.hotel.hotel_management.repository.PaymentStatusRepository;
import com.hotel.hotel_management.service.PaymentStatusService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentStatusResource {

    private final Logger log = LoggerFactory.getLogger(PaymentStatusResource.class);
    private final PaymentStatusService paymentStatusService;

    @GetMapping("/payment-statuses")
    public List<PaymentStatus> getAllPaymentStatuses() {
        log.debug("REST request to get all PaymentStatuses");
        return paymentStatusService.findAll();
    }
}
