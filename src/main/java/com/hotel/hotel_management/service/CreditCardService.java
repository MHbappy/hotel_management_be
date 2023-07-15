package com.hotel.hotel_management.service;

import com.hotel.hotel_management.configuration.Constrains;
import com.hotel.hotel_management.model.CreditCard;
import com.hotel.hotel_management.model.Users;
import com.hotel.hotel_management.repository.CreditCardRepository;
import com.hotel.hotel_management.repository.ReservationRepository;
import com.hotel.hotel_management.repository.UserRepository;
import com.hotel.hotel_management.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreditCardService {

    private final Logger log = LoggerFactory.getLogger(CreditCardService.class);

    private final CreditCardRepository creditCardRepository;
    private final UserRepository userRepository;

    public CreditCard save(CreditCard creditCard) {
        log.debug("Request to save CreditCard : {}", creditCard);
        return creditCardRepository.save(creditCard);
    }

    public CreditCard update(CreditCard creditCard) {
        log.debug("Request to update CreditCard : {}", creditCard);

        Boolean isGuest = SecurityUtils.hasCurrentUserThisAuthority(Constrains.guest);
        if (isGuest) {
            String userName = SecurityUtils.getCurrentUserLogin().get();
            Users user = userRepository.findByEmail(userName);
            creditCard.setUsers(user);
        }
        return creditCardRepository.save(creditCard);
    }
    
    @Transactional(readOnly = true)
    public List<CreditCard> findAll(Integer userId) {
        log.debug("Request to get all CreditCards");
        if (userId != null){
            return creditCardRepository.findAllByIsActiveTrueAndUsers_Id(userId);
        }
        return creditCardRepository.findAllByIsActiveTrue();
    }

    
    @Transactional(readOnly = true)
    public Optional<CreditCard> findOne(Long id) {
        log.debug("Request to get CreditCard : {}", id);
        return creditCardRepository.findById(id);
    }

    
    public void delete(Long id) {
        log.debug("Request to delete CreditCard : {}", id);
        CreditCard creditCard = creditCardRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credit card not found!"));
        creditCard.setIsActive(true);
        creditCardRepository.save(creditCard);
    }
}
