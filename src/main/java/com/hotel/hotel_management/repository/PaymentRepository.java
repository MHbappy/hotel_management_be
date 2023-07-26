package com.hotel.hotel_management.repository;

import com.hotel.hotel_management.model.Payment;
import com.hotel.hotel_management.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Payment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByUsers(Users users);

    @Query(value = "select * from payment inner join users u on u.id = payment.users_id AND u.email like :email", nativeQuery = true)
    List<Payment> findAllByUsersEmailLike(@Param("email") String email);
}
