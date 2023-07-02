package com.hotel.hotel_management.dto;
import java.util.List;

import com.hotel.hotel_management.model.Users;
import lombok.Data;

@Data
public class UserResponseDTO {
  private Integer id;
  private String username;
  private String email;
  List<Users> appUserRoles;
}
