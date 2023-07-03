package com.hotel.hotel_management.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

/**
 * A Room.
 */
@Entity
@Table(name = "room")
@Data
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "room_number")
    private String roomNumber;

    @Column(name = "floot")
    private String floot;

    @Column(name = "price_per_night")
    private Double pricePerNight;

    @Column(name = "max_guests")
    private Integer maxGuests;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "image")
    @Lob
    private byte[] image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "rooms", "specialOffers" }, allowSetters = true)
    private RoomType roomType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "rooms" }, allowSetters = true)
    private RoomStatus roomStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "rooms" }, allowSetters = true)
    private RoomAvailabilityStatus roomAvailabilityStatus;
}
