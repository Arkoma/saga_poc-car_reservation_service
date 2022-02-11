package com.saga.saga_poc__car_reservation_service.service;

import com.saga.saga_poc__car_reservation_service.repository.CarRepository;
import com.saga.saga_poc__car_reservation_service.repository.CarReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarReservationServiceIT {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CarReservationService underTest;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarReservationRepository carReservationRepository;

    @Test
    void carReservationServiceBeanGetsCreated() {
        assertTrue(applicationContext.containsBean("carReservationService"));
    }

    @Test
    void carReservationServiceContainsCarRepository() {
        CarRepository injectedCarRepository = (CarRepository) ReflectionTestUtils.getField(underTest, "carRepository");
        assertSame(carRepository, injectedCarRepository);
    }

    @Test
    void carReservationServiceContainsCarReservationRepository() {
        CarReservationRepository injectedCarReservationRepository =(CarReservationRepository) ReflectionTestUtils.getField(underTest, "carReservationRepository");
        assertSame(carReservationRepository, injectedCarReservationRepository);
    }

}