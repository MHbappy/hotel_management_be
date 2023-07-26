package com.hotel.hotel_management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Size(min = 1, message = "Minimum name length: 3 characters")
    @Column(name = "title")
    private String title;

    @Size(min = 1, message = "Minimum name length: 3 characters")
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

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "image")
    @JsonIgnore
    private byte[] image;

    @ManyToOne
    private RoomType roomType;

    @ManyToOne
    private RoomStatus roomStatus;

    private Boolean isActive;
}
