package com.hotel.hotel_management.dto;

import com.hotel.hotel_management.model.Roles;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class GuestUserDataDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthOfDate;
    private String address;
    private String country;
    private String city;
    private String zipCode;
}
