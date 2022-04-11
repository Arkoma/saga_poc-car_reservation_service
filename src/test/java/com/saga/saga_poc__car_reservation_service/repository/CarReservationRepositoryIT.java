package com.saga.saga_poc__car_reservation_service.repository;

import com.saga.saga_poc__car_reservation_service.model.CarReservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarReservationRepositoryIT {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CarReservationRepository underTest;

    @BeforeEach
    void setup() {
        underTest.deleteAll();
    }

    @Test
    void carReservationRepositoryIsABean() {
        assertTrue(applicationContext.containsBean("carReservationRepository"));
    }

    @Test
    void carReservationNullIfCarReservationByReservationIdNotFound() {
        CarReservation carReservation = underTest.findByReservationId(1L);
        assertNull(carReservation);
    }

    @Test
    void carReservationFoundIfCarReservationAlreadySavedWithReservationId() {
        final Long reservationId = 1L;
        CarReservation reservationToSave = new CarReservation();
        reservationToSave.setReservationId(reservationId);
        CarReservation savedReservation = underTest.save(reservationToSave);
        CarReservation foundReservation = underTest.findByReservationId(reservationId);
        assertEquals(savedReservation.getId(), foundReservation.getId());
    }


}