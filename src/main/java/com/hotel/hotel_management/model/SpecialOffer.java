package com.hotel.hotel_management.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import javax.persistence.*;
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

    @Column(name = "offer_name")
    private String offerName;

    @Column(name = "offer_code")
    private String offerCode;

    @Column(name = "discount_percent")
    private Double discountPercent;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "rooms", "specialOffers" }, allowSetters = true)
    private RoomType roomType;
}
