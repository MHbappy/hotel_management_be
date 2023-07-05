package com.hotel.hotel_management.dto;

import com.hotel.hotel_management.model.Roles;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UserUpdateDataDTO{
    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthOfDate;
    private String address;
    private String country;
    private String city;
    private String zipCode;
    private Set<Roles> appRoles = new HashSet<>();
}
