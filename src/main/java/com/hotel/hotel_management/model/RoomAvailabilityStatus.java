package com.hotel.hotel_management.model;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

/**
 * A RoomAvailabilityStatus.
 */
@Entity
@Table(name = "room_availability_status")
@Data
public class RoomAvailabilityStatus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_active")
    private Boolean isActive;
}
