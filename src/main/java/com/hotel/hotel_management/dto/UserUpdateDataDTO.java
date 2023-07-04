package com.hotel.hotel_management.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateDataDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthOfDate;
    private String address;
    private String country;
    private String city;
    private String zipCode;
}
