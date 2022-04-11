package com.saga.saga_poc__car_reservation_service.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CarReservationRequest {
    private String carMake;
    private String carModel;
    private Long reservationId;
    private String agency;
    private Date checkinDate;
    private Date checkoutDate;
}
