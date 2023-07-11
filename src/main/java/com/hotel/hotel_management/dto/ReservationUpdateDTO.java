package com.hotel.hotel_management.dto;

import com.hotel.hotel_management.enumuration.ReservationStatus;
import lombok.Data;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Data
public class ReservationUpdateDTO {
    Long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;
    @NotNull
    private Integer numberOfGuests;
}
