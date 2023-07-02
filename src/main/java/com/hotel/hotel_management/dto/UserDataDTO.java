package com.hotel.hotel_management.dto;

import java.util.List;

import com.hotel.hotel_management.model.Roles;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDataDTO {
  
  private String username;
  private String email;
  private String password;
  List<Roles> appUserRoles;

}
