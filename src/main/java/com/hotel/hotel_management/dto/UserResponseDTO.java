package com.hotel.hotel_management.dto;
import java.time.LocalDate;
import java.util.List;
import com.hotel.hotel_management.model.Roles;
import lombok.Data;

@Data
public class UserResponseDTO extends GuestUserDataDTO{
  private Integer id;
  List<Roles> roles;
}
