package com.hotel.hotel_management.repository;
import com.hotel.hotel_management.model.SpecialOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the SpecialOffer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecialOfferRepository extends JpaRepository<SpecialOffer, Long> {
    Boolean existsByOfferCodeAndIsActiveTrue(String code);
    List<SpecialOffer> findAllByIsActiveTrue();
    @Query(value = "select * from special_offer where :date between start_date AND end_date AND room_type_id = :roomTypeId AND is_active = true AND offer_code = :code LIMIT 1", nativeQuery = true)
    SpecialOffer findAllByRoomTypeAndDateBetween(@Param("code") String code, @Param("date") LocalDate date, @Param("roomTypeId")Integer roomTypeId);
}
