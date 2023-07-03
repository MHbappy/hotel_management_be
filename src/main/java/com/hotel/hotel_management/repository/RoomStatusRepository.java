package com.hotel.hotel_management.repository;

import com.hotel.hotel_management.model.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RoomStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomStatusRepository extends JpaRepository<RoomStatus, Long> {}
