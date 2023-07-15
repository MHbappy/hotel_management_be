package com.hotel.hotel_management.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotel.hotel_management.enumuration.ReservationStatus;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A Reservation.
 */
@Entity
@Table(name = "reservation")
@Data
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "number_of_guests")
    private Integer numberOfGuests;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @Transient
    private LocalDateTime currentDateTime = LocalDateTime.now();

    @ManyToOne
    @NotNull
    private Room room;

    @ManyToOne
    private CheckInOut checkInOut;

    @ManyToOne
    private Users users;
}
