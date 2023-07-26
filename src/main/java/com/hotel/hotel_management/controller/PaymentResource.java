package com.hotel.hotel_management.controller;

import com.hotel.hotel_management.model.Payment;
import com.hotel.hotel_management.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentResource {

    private final Logger log = LoggerFactory.getLogger(PaymentResource.class);
    private final PaymentService paymentService;

    @GetMapping("/payments")
    public List<Payment> getAllPayments(@RequestParam(required = false) String userName) {
        log.debug("REST request to get all Payments");
        return paymentService.findAll(userName);
    }
}
