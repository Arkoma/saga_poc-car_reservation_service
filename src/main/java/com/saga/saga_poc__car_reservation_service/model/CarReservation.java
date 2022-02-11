package com.saga.saga_poc__car_reservation_service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
public class CarReservation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Long reservationId;
    private StatusEnum status;
    private Long carId;
    private String agency;
    private Date checkinDate;
    private Date checkoutDate;
}
