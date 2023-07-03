package com.hotel.hotel_management.controller;

import com.hotel.hotel_management.model.PaymentStatus;
import com.hotel.hotel_management.repository.PaymentStatusRepository;
import com.hotel.hotel_management.service.PaymentStatusService;
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
public class PaymentStatusResource {

    private final Logger log = LoggerFactory.getLogger(PaymentStatusResource.class);

    private final PaymentStatusService paymentStatusService;

    private final PaymentStatusRepository paymentStatusRepository;

    public PaymentStatusResource(PaymentStatusService paymentStatusService, PaymentStatusRepository paymentStatusRepository) {
        this.paymentStatusService = paymentStatusService;
        this.paymentStatusRepository = paymentStatusRepository;
    }

    @PostMapping("/payment-statuses")
    public ResponseEntity<PaymentStatus> createPaymentStatus(@RequestBody PaymentStatus paymentStatus) throws URISyntaxException {
        log.debug("REST request to save PaymentStatus : {}", paymentStatus);
        if (paymentStatus.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new paymentStatus cannot already have an ID");
        }
        PaymentStatus result = paymentStatusService.save(paymentStatus);
        return ResponseEntity
            .created(new URI("/api/payment-statuses/" + result.getId()))
            .body(result);
    }

    @PutMapping("/payment-statuses/{id}")
    public ResponseEntity<PaymentStatus> updatePaymentStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentStatus paymentStatus
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentStatus : {}, {}", id, paymentStatus);
        if (paymentStatus.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!Objects.equals(id, paymentStatus.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }

        if (!paymentStatusRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }

        PaymentStatus result = paymentStatusService.update(paymentStatus);
        return ResponseEntity
            .ok()
            .body(result);
    }


    @GetMapping("/payment-statuses")
    public List<PaymentStatus> getAllPaymentStatuses() {
        log.debug("REST request to get all PaymentStatuses");
        return paymentStatusService.findAll();
    }

    @GetMapping("/payment-statuses/{id}")
    public ResponseEntity<PaymentStatus> getPaymentStatus(@PathVariable Long id) {
        log.debug("REST request to get PaymentStatus : {}", id);
        Optional<PaymentStatus> paymentStatus = paymentStatusService.findOne(id);
        return ResponseEntity.ok(paymentStatus.get());
    }

    @DeleteMapping("/payment-statuses/{id}")
    public ResponseEntity<Void> deletePaymentStatus(@PathVariable Long id) {
        log.debug("REST request to delete PaymentStatus : {}", id);
        paymentStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
