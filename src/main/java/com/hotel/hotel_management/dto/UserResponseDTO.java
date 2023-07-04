package com.hotel.hotel_management.dto;
import java.time.LocalDate;
import java.util.List;
import com.hotel.hotel_management.model.Roles;
import lombok.Data;

@Data
public class UserResponseDTO {
  private Integer id;
  private String email;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private LocalDate birthOfDate;
  private String address;
  private String country;
  private String city;
  private String zipCode;
  List<Roles> roles;
}
