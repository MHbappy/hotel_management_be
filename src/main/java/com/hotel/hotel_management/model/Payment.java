package com.hotel.hotel_management.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@Data
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "payment_date_time")
    private LocalDateTime paymentDateTime;

    @ManyToOne
    private Users users;

    @ManyToOne
    private Reservation reservation;

    @ManyToOne
    private PaymentStatus paymentStatus;

    @ManyToOne
    private CreditCard creditCard;
}
