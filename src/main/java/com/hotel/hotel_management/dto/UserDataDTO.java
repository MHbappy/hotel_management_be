package com.hotel.hotel_management.dto;

import java.time.LocalDate;
import java.util.List;

import com.hotel.hotel_management.model.Roles;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserDataDTO extends GuestUserDataDTO{
  List<Roles> roles;
}
