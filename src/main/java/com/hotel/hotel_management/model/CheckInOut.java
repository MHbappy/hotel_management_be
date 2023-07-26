package com.hotel.hotel_management.model;

import com.hotel.hotel_management.enumuration.CheckInStatus;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A CheckInOut.
 */
@Entity
@Table(name = "check_in_out")
@Data
public class CheckInOut implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time")
    private LocalDateTime endDateTime;

    private CheckInStatus checkInStatus;

}
