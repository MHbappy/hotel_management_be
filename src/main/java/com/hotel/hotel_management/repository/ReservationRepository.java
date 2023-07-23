package com.hotel.hotel_management.repository;

import com.hotel.hotel_management.model.Reservation;
import com.hotel.hotel_management.model.Users;
import com.hotel.hotel_management.model.projection.DashBoardCardInfo;
import com.hotel.hotel_management.model.projection.DayWiseReservationCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Reservation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByUsers(Users users);
    @Query(value = "select count(id), start_date as startDate from reservation where start_date > now() - interval '30 day' group by start_date order by start_date asc", nativeQuery = true)
    List<DayWiseReservationCount> getDayWiseReservation();
    @Query(value = "select count(room_type_id), rs.name\n" +
            "from room\n" +
            "         inner join room_status rs on rs.id = room.room_status_id\n" +
            "group by rs.name\n" +
            "UNION\n" +
            "select count(room_type_id), rt.name\n" +
            "from room\n" +
            "         inner join room_type rt on rt.id = room.room_type_id\n" +
            "group by rt.name\n" +
            "UNION\n" +
            "select count(r.id), 'RESERVED' as name\n" +
            "from room r\n" +
            "         left join reservation r2 on r.id = r2.room_id\n" +
            "where now() between start_date AND end_date\n" +
            "  AND r.is_active = true\n" +
            "UNION\n" +
            "select count(id), 'TOTAL ROOM' as Name from room where is_active = true", nativeQuery = true)
    List<DashBoardCardInfo> getDashBoardCardInfo();

}
