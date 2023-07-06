package com.hotel.hotel_management.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A SpecialOffer.
 */
@Entity
@Table(name = "special_offer")
@Data
public class SpecialOffer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(min = 3, message = "Minimum name length: 3 characters")
    @Column(name = "offer_name")
    private String offerName;

    @Size(min = 3, message = "Minimum name length: 3 characters")
    @Column(name = "offer_code")
    private String offerCode;

    @Column(name = "discount_percent")
    @NotNull
    private Double discountPercent;

    @NotNull
    @Column(name = "start_date")
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date")
    private LocalDate endDate;

    private Boolean isActive;

    @ManyToOne
    private RoomType roomType;
}
