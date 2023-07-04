package com.hotel.hotel_management.repository;

import javax.transaction.Transactional;

import com.hotel.hotel_management.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer> {
  boolean existsByEmail(String email);
  Users findByEmail(String email);
  Page<Users> findAllByIsActiveAndEmailContainingIgnoreCase(Boolean isActive, String email, Pageable pageable);
  @Transactional
  void deleteByEmail(String email);
}
