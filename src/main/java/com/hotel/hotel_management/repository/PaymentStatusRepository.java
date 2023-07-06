package com.hotel.hotel_management.repository;

import com.hotel.hotel_management.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the PaymentStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {
    List<PaymentStatus> findAllByIsActiveTrue();
}
