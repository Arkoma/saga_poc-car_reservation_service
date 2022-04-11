package com.saga.saga_poc__car_reservation_service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
public class CarReservation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private StatusEnum status;
    private Long reservationId;
    private Long carId;
    private String carMake;
    private String carModel;
    private String agency;
    private Date checkinDate;
    private Date checkoutDate;
}
