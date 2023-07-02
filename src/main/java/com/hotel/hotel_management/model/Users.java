package com.hotel.hotel_management.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Table(name = "users")
@Entity
@Data
@NoArgsConstructor
public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(unique = true, nullable = false)
  private String email;

  private String firstName;

  private String lastName;

  private String phoneNumber;

  private LocalDate birthOfDate;

  private String address;

  private String country;

  private String city;

  private String zipCode;

  private Boolean isActive;

  @Size(min = 8, message = "Minimum password length: 8 characters")
  private String password;

  @ManyToMany
  @JoinTable(
          name = "user_role",
          joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
          inverseJoinColumns = {@JoinColumn(name = "role_name", referencedColumnName = "name")})
  private Set<Roles> roles = new HashSet<>();

}
