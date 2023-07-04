package com.hotel.hotel_management.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class PasswordChangeDTO {
    private Integer userId;
    @Size(min = 6, message = "Minimum First name length: 6 characters")
    private String password;
}