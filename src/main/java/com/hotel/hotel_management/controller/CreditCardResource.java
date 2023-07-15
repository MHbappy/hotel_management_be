package com.hotel.hotel_management.controller;

import com.hotel.hotel_management.model.CreditCard;
import com.hotel.hotel_management.repository.CreditCardRepository;
import com.hotel.hotel_management.service.CreditCardService;
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
public class CreditCardResource {

    private final Logger log = LoggerFactory.getLogger(CreditCardResource.class);
    private final CreditCardService creditCardService;
    private final CreditCardRepository creditCardRepository;

    public CreditCardResource(CreditCardService creditCardService, CreditCardRepository creditCardRepository) {
        this.creditCardService = creditCardService;
        this.creditCardRepository = creditCardRepository;
    }

    @PostMapping("/credit-cards")
    public ResponseEntity<CreditCard> createCreditCard(@RequestBody CreditCard creditCard) throws URISyntaxException {
        log.debug("REST request to save CreditCard : {}", creditCard);
        if (creditCard.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new creditCard cannot already have an ID");
        }
        CreditCard result = creditCardService.save(creditCard);
        return ResponseEntity
            .created(new URI("/api/credit-cards/" + result.getId()))
            .body(result);
    }

    @PutMapping("/credit-cards/{id}")
    public ResponseEntity<CreditCard> updateCreditCard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CreditCard creditCard
    ) throws URISyntaxException {
        log.debug("REST request to update CreditCard : {}, {}", id, creditCard);
        if (creditCard.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!Objects.equals(id, creditCard.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }

        if (!creditCardRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }

        CreditCard result = creditCardService.update(creditCard);
        return ResponseEntity
            .ok()
            .body(result);
    }

    @GetMapping("/credit-cards")
    public List<CreditCard> getAllCreditCards(@RequestParam(required = false) Long userId) {
        log.debug("REST request to get all CreditCards");
        return creditCardService.findAll(userId);
    }

    @GetMapping("/credit-cards/{id}")
    public ResponseEntity<CreditCard> getCreditCard(@PathVariable Long id) {
        log.debug("REST request to get CreditCard : {}", id);
        Optional<CreditCard> creditCard = creditCardService.findOne(id);
        return ResponseEntity.ok(creditCard.get());
    }

    @DeleteMapping("/credit-cards/{id}")
    public ResponseEntity<Void> deleteCreditCard(@PathVariable Long id) {
        log.debug("REST request to delete CreditCard : {}", id);
        creditCardService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
