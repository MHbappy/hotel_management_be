package com.hotel.hotel_management.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A Room.
 */
@Entity
@Table(name = "room")
@Data
public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(min = 3, message = "Minimum name length: 3 characters")
    @Column(name = "title")
    private String title;

    @Size(min = 3, message = "Minimum name length: 3 characters")
    @Column(name = "room_number")
    private String roomNumber;

    @Column(name = "floor")
    @NotNull
    private String floor;

    @Column(name = "price_per_night")
    @NotNull
    private Double pricePerNight;

    @Column(name = "max_guests")
    @NotNull
    private Integer maxGuests;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "image")
    @Lob
    private byte[] image;

    @ManyToOne
    private RoomType roomType;

    @ManyToOne
    private RoomStatus roomStatus;

    @ManyToOne
    private RoomAvailabilityStatus roomAvailabilityStatus;
}
