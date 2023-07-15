package com.hotel.hotel_management.repository;

import com.hotel.hotel_management.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the Room entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByIsActiveTrue();
    List<Room> findAllByRoomStatus_IdAndIsActiveTrue(Long id);
    @Query(value = "select * from room r where room_status_id = :roomStatusId AND :price <= price_per_night AND r.is_active = true", nativeQuery = true)
    List<Room> getRoomByRoomStatusIdPrice(@Param("roomStatusId") Long roomStatusId, @Param("price") Long price);
    @Query(value = "select r.* from room r left join reservation r2 on r.id = r2.room_id where room_status_id = :roomStatusId AND :price <= price_per_night AND :date between start_date AND end_date AND r.is_active = true", nativeQuery = true)
    List<Room> getRoomByRoomStatusIdDateBetweenAndPriceRange(@Param("roomStatusId") Long roomStatusId, @Param("price") Long price, @Param("date") LocalDate date);
}
