package com.hotel.hotel_management.repository;

import com.hotel.hotel_management.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Query(value = "select r.* from room r left join reservation r2 on r.id = r2.room_id where :date between start_date AND end_date AND r.is_active = true", nativeQuery = true)
    List<Room> findAllByDate(@Param("date") LocalDate date);
    @Query(value = "select * from room r where room_status_id = :roomStatusId AND :price <= price_per_night AND r.is_active = true", nativeQuery = true)
    List<Room> getRoomByRoomStatusIdPrice(@Param("roomStatusId") Long roomStatusId, @Param("price") Long price);
    Page<Room> findAllByIsActiveTrue(Pageable pageable);
    Page<Room> findAllByTitleContainingIgnoreCaseAndIsActiveTrue(String title, Pageable pageable);
    @Query(value = "select * from room where room_number = :roomNumber AND is_active = true LIMIT 1", nativeQuery = true)
    Room findByRoomNumberAndIsActiveLimitOne(@Param("roomNumber")String roomNumber);
    @Query(value = "select r.* from room r left join reservation r2 on r.id = r2.room_id where ((:date NOT between start_date AND end_date) OR (start_date IS NULL)) AND r.is_active = true", nativeQuery = true)
    Page<Room> getRoomByRoomDateBetween(@Param("date") LocalDate date, Pageable pageable);
    @Query(value = "select r.* from room r left join reservation r2 on r.id = r2.room_id where ((:date NOT between start_date AND end_date) OR (start_date IS NULL)) AND room_status_id = :roomStatusId AND r.is_active = true", nativeQuery = true)
    Page<Room> getRoomByRoomDateBetweenAndStatusId(@Param("date") LocalDate date, @Param("roomStatusId") Long roomStatusId, Pageable pageable);
    @Query(value = "select r.* from room r left join reservation r2 on r.id = r2.room_id where ((:date NOT between start_date AND end_date) OR (start_date IS NULL)) AND room_status_id = :roomStatusId AND (price_per_night between :startPrice AND :endPrice) AND r.is_active = true", nativeQuery = true)
    Page<Room> getRoomByRoomDateBetweenAndStatusIdAndPriceRange(@Param("date") LocalDate date, @Param("roomStatusId") Long roomStatusId, @Param("startPrice") Long startPrice, @Param("endPrice") Long endPrice, Pageable pageable);
    @Query(value = "select r.* from room r left join reservation r2 on r.id = r2.room_id where ((:startDate NOT between start_date AND end_date) AND (:endDate NOT between start_date AND end_date)) AND r.is_active = true", nativeQuery = true)
    List<Room> getAvailableRoomWithDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
