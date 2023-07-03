package com.hotel.hotel_management.repository;
import com.hotel.hotel_management.model.SpecialOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SpecialOffer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecialOfferRepository extends JpaRepository<SpecialOffer, Long> {}
