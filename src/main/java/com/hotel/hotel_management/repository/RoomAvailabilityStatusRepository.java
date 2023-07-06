package com.hotel.hotel_management.repository;

import com.hotel.hotel_management.model.RoomAvailabilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the RoomAvailabilityStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomAvailabilityStatusRepository extends JpaRepository<RoomAvailabilityStatus, Long> {
    List<RoomAvailabilityStatus> findAllByIsActiveTrue();
}
