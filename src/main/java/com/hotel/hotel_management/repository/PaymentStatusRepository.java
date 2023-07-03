package com.hotel.hotel_management.repository;

import com.hotel.hotel_management.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PaymentStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {}
