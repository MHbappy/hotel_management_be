package com.hotel.hotel_management.dto;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ReservationDTO {
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private Integer numberOfGuests;
    @NotNull
    private Long roomId;

    private Long userId;
    @NotNull
    private Long cardId;
    @NotNull
    private Double price;
}
