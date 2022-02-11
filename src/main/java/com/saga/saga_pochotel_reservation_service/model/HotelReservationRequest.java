package com.saga.saga_pochotel_reservation_service.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class HotelReservationRequest {
    private Hotel hotel;
    private Long reservationId;
    private int room;
    private Date checkinDate;
    private Date checkoutDate;
}
