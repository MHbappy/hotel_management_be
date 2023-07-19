package com.hotel.hotel_management.controller;

import com.hotel.hotel_management.model.SpecialOffer;
import com.hotel.hotel_management.repository.SpecialOfferRepository;
import com.hotel.hotel_management.service.SpecialOfferService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SpecialOfferResource {
    private final Logger log = LoggerFactory.getLogger(SpecialOfferResource.class);
    private final SpecialOfferService specialOfferService;
    private final SpecialOfferRepository specialOfferRepository;

    @PostMapping("/special-offers")
    public ResponseEntity<SpecialOffer> createSpecialOffer(@RequestBody SpecialOffer specialOffer) throws URISyntaxException {
        log.debug("REST request to save SpecialOffer : {}", specialOffer);
        if (specialOffer.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new specialOffer cannot already have an ID");
        }
        Boolean isExistWithSameCode = specialOfferRepository.existsByOfferCodeAndIsActiveTrue(specialOffer.getOfferCode());
        if (isExistWithSameCode){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Offer code already exist!");
        }
        SpecialOffer result = specialOfferService.save(specialOffer);
        return ResponseEntity
            .created(new URI("/api/special-offers/" + result.getId()))
            .body(result);
    }

    @PutMapping("/special-offers/{id}")
    public ResponseEntity<SpecialOffer> updateSpecialOffer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SpecialOffer specialOffer
    ) throws URISyntaxException {
        log.debug("REST request to update SpecialOffer : {}, {}", id, specialOffer);
        if (specialOffer.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!Objects.equals(id, specialOffer.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }

        if (!specialOfferRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }

        SpecialOffer result = specialOfferService.update(specialOffer);
        return ResponseEntity
            .ok()
            .body(result);
    }

    @GetMapping("/special-offers")
    public List<SpecialOffer> getAllSpecialOffers() {
        log.debug("REST request to get all SpecialOffers");
        return specialOfferService.findAll();
    }

    @GetMapping("/special-offers/{id}")
    public ResponseEntity<SpecialOffer> getSpecialOffer(@PathVariable Long id) {
        log.debug("REST request to get SpecialOffer : {}", id);
        Optional<SpecialOffer> specialOffer = specialOfferService.findOne(id);
        return ResponseEntity.ok(specialOffer.get());
    }

    @GetMapping("/special-offers-by-date-and-room-type")
    public SpecialOffer findAllByRoomTypeAndDateBetween(@RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestParam(value = "roomTypeId") Integer roomTypeId){
        return specialOfferService.findAllByRoomTypeAndDateBetween(date, roomTypeId);
    }

    /**
     * {@code DELETE  /special-offers/:id} : delete the "id" specialOffer.
     * @param id the id of the specialOffer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/special-offers/{id}")
    public ResponseEntity<Void> deleteSpecialOffer(@PathVariable Long id) {
        log.debug("REST request to delete SpecialOffer : {}", id);
        specialOfferService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
