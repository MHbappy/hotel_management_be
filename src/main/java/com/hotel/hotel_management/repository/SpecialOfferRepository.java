package com.hotel.hotel_management.repository;
import com.hotel.hotel_management.model.SpecialOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the SpecialOffer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecialOfferRepository extends JpaRepository<SpecialOffer, Long> {
    Boolean existsByOfferCodeAndIsActiveTrue(String code);
    List<SpecialOffer> findAllByIsActiveTrue();
}
