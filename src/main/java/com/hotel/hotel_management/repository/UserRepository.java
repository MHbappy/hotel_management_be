package com.hotel.hotel_management.repository;

import javax.transaction.Transactional;

import com.hotel.hotel_management.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Integer> {
  boolean existsByEmail(String email);
  Users findByEmail(String email);
  Page<Users> findAllByIsActiveAndEmailContainingIgnoreCase(Boolean isActive, String email, Pageable pageable);
  List<Users> findAllByIsActiveAndEmailContainingIgnoreCase(Boolean isActive, String email);
  @Transactional
  void deleteByEmail(String email);
}
