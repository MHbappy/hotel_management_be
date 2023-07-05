package com.hotel.hotel_management.dto;

import com.hotel.hotel_management.model.Roles;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
public class GuestUserDataDTO {
    @Email
    private String email;
    @Size(min = 6, message = "Minimum name length: 6 characters")
    private String password;
    @Size(min = 3, message = "Minimum name length: 3 characters")
    private String firstName;
    private String lastName;
    @Size(min = 3, message = "Minimum name length: 3 characters")
    private String phoneNumber;
    private LocalDate birthOfDate;
    private String address;
    private String country;
    private String city;
    private String zipCode;
}
