package com.hotel.hotel_management.repository;


import com.hotel.hotel_management.model.CheckInOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CheckInOut entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckInOutRepository extends JpaRepository<CheckInOut, Long> {}
