package com.saga.saga_pochotel_reservation_service.controller;

import com.saga.saga_pochotel_reservation_service.model.HotelReservation;
import com.saga.saga_pochotel_reservation_service.model.HotelReservationRequest;
import com.saga.saga_pochotel_reservation_service.service.HotelReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HotelReservationController {

    private final HotelReservationService hotelReservationService;

    public HotelReservationController(HotelReservationService hotelReservationService) {
        this.hotelReservationService = hotelReservationService;
    }


    @PostMapping("/reservation")
    public ResponseEntity<HotelReservation> makeReservation(@RequestBody HotelReservationRequest request) {
        HotelReservation hotelReservation = this.hotelReservationService.makeReservation(request);
        return new ResponseEntity<>(hotelReservation, HttpStatus.CREATED);
    }

    @DeleteMapping("/reservation/{id}")
    public ResponseEntity cancelReservation(@PathVariable Long id) {
        this.hotelReservationService.cancelReservation(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<HotelReservation> getReservationById(@PathVariable Long id) {
        HotelReservation reservationById = this.hotelReservationService.getReservationById(id);
        return new ResponseEntity<> (reservationById, null == reservationById ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<HotelReservation>> getAllReservations() {
        return new ResponseEntity<>(this.hotelReservationService.getAllReservations(), HttpStatus.OK);
    }
}
