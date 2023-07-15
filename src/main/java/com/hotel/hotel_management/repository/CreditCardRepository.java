package com.hotel.hotel_management.repository;

import com.hotel.hotel_management.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the CreditCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    List<CreditCard> findAllByIsActiveTrue();
    List<CreditCard> findAllByIsActiveTrueAndUsers_Id(Long userId);
}
