package com.hotel.hotel_management.model;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

/**
 * A RoomStatus.
 */
@Entity
@Table(name = "room_status")
@Data
public class RoomStatus implements Serializable {

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
