package com.hotel.hotel_management.service;

import com.hotel.hotel_management.model.SpecialOffer;
import com.hotel.hotel_management.repository.SpecialOfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SpecialOfferService {

    private final Logger log = LoggerFactory.getLogger(SpecialOfferService.class);

    private final SpecialOfferRepository specialOfferRepository;

    public SpecialOfferService(SpecialOfferRepository specialOfferRepository) {
        this.specialOfferRepository = specialOfferRepository;
    }

    
    public SpecialOffer save(SpecialOffer specialOffer) {
        log.debug("Request to save SpecialOffer : {}", specialOffer);
        return specialOfferRepository.save(specialOffer);
    }

    
    public SpecialOffer update(SpecialOffer specialOffer) {
        log.debug("Request to update SpecialOffer : {}", specialOffer);
        return specialOfferRepository.save(specialOffer);
    }

    
    public Optional<SpecialOffer> partialUpdate(SpecialOffer specialOffer) {
        log.debug("Request to partially update SpecialOffer : {}", specialOffer);

        return specialOfferRepository
            .findById(specialOffer.getId())
            .map(existingSpecialOffer -> {
                if (specialOffer.getOfferName() != null) {
                    existingSpecialOffer.setOfferName(specialOffer.getOfferName());
                }
                if (specialOffer.getOfferCode() != null) {
                    existingSpecialOffer.setOfferCode(specialOffer.getOfferCode());
                }
                if (specialOffer.getDiscountPercent() != null) {
                    existingSpecialOffer.setDiscountPercent(specialOffer.getDiscountPercent());
                }
                if (specialOffer.getStartDate() != null) {
                    existingSpecialOffer.setStartDate(specialOffer.getStartDate());
                }
                if (specialOffer.getEndDate() != null) {
                    existingSpecialOffer.setEndDate(specialOffer.getEndDate());
                }

                return existingSpecialOffer;
            })
            .map(specialOfferRepository::save);
    }

    
    @Transactional(readOnly = true)
    public List<SpecialOffer> findAll() {
        log.debug("Request to get all SpecialOffers");
        return specialOfferRepository.findAll();
    }

    
    @Transactional(readOnly = true)
    public Optional<SpecialOffer> findOne(Long id) {
        log.debug("Request to get SpecialOffer : {}", id);
        return specialOfferRepository.findById(id);
    }

    
    public void delete(Long id) {
        log.debug("Request to delete SpecialOffer : {}", id);
        specialOfferRepository.deleteById(id);
    }
}
