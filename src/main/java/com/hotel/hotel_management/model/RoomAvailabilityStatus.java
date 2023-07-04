package com.hotel.hotel_management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A RoomAvailabilityStatus.
 */
@Entity
@Table(name = "room_availability_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomAvailabilityStatus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_active")
    private Boolean isActive;
}
