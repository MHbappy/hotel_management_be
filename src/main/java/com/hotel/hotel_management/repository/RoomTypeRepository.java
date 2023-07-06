package com.hotel.hotel_management.repository;

import com.hotel.hotel_management.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the RoomType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
    List<RoomType> findAllByIsActiveTrue();
}
