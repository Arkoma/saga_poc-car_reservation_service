package com.saga.saga_poc__car_reservation_service.controller;

import com.saga.saga_poc__car_reservation_service.model.CarReservation;
import com.saga.saga_poc__car_reservation_service.model.CarReservationRequest;
import com.saga.saga_poc__car_reservation_service.service.CarReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.*;

@RestController
public class CarReservationController {

    private final CarReservationService carReservationService;

    public CarReservationController(CarReservationService carReservationService) {
        this.carReservationService = carReservationService;
    }


    @PostMapping("/reservation")
    public ResponseEntity<CarReservation> makeReservation(@RequestBody CarReservationRequest request) {
        CarReservation carReservation = this.carReservationService.makeReservation(request);
        return new ResponseEntity<>(carReservation, HttpStatus.CREATED);
    }

    @DeleteMapping("/reservation/{id}")
    public ResponseEntity<String> cancelReservation(@PathVariable Long id) {
        this.carReservationService.cancelReservation(id);
        return new ResponseEntity<>("Car reservation cancelled", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<CarReservation> getReservationById(@PathVariable Long id) {
        CarReservation reservationById = this.carReservationService.getReservationById(id);
        return new ResponseEntity<> (reservationById, null == reservationById ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<CarReservation>> getAllReservations() {
        return new ResponseEntity<>(this.carReservationService.getAllReservations(), HttpStatus.OK);
    }
}
