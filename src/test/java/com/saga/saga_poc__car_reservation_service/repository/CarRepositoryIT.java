package com.saga.saga_poc__car_reservation_service.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarRepositoryIT {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void carRepositoryBeanExists() {
        assertTrue(applicationContext.containsBean("carRepository"));
    }

}