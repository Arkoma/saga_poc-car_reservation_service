package com.saga.saga_pochotel_reservation_service.model;

import lombok.Data;

import javax.persistence.*;

import java.util.Date;

@Data
@Entity
public class HotelReservation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Long reservationId;
    private StatusEnum status;
    private Long hotelId;
    private int room;
    private Date checkinDate;
    private Date checkoutDate;
}
