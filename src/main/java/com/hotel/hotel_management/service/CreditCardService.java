package com.hotel.hotel_management.service;

import com.hotel.hotel_management.model.CreditCard;
import com.hotel.hotel_management.repository.CreditCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CreditCardService {

    private final Logger log = LoggerFactory.getLogger(CreditCardService.class);

    private final CreditCardRepository creditCardRepository;

    public CreditCardService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    
    public CreditCard save(CreditCard creditCard) {
        log.debug("Request to save CreditCard : {}", creditCard);
        return creditCardRepository.save(creditCard);
    }

    
    public CreditCard update(CreditCard creditCard) {
        log.debug("Request to update CreditCard : {}", creditCard);
        return creditCardRepository.save(creditCard);
    }

    
    public Optional<CreditCard> partialUpdate(CreditCard creditCard) {
        log.debug("Request to partially update CreditCard : {}", creditCard);

        return creditCardRepository
            .findById(creditCard.getId())
            .map(existingCreditCard -> {
                if (creditCard.getCardNumber() != null) {
                    existingCreditCard.setCardNumber(creditCard.getCardNumber());
                }
                if (creditCard.getCardHolderName() != null) {
                    existingCreditCard.setCardHolderName(creditCard.getCardHolderName());
                }
                if (creditCard.getExpirationDate() != null) {
                    existingCreditCard.setExpirationDate(creditCard.getExpirationDate());
                }
                if (creditCard.getCvv() != null) {
                    existingCreditCard.setCvv(creditCard.getCvv());
                }
                if (creditCard.getCustomerId() != null) {
                    existingCreditCard.setCustomerId(creditCard.getCustomerId());
                }

                return existingCreditCard;
            })
            .map(creditCardRepository::save);
    }

    
    @Transactional(readOnly = true)
    public List<CreditCard> findAll() {
        log.debug("Request to get all CreditCards");
        return creditCardRepository.findAll();
    }

    
    @Transactional(readOnly = true)
    public Optional<CreditCard> findOne(Long id) {
        log.debug("Request to get CreditCard : {}", id);
        return creditCardRepository.findById(id);
    }

    
    public void delete(Long id) {
        log.debug("Request to delete CreditCard : {}", id);
        creditCardRepository.deleteById(id);
    }
}
