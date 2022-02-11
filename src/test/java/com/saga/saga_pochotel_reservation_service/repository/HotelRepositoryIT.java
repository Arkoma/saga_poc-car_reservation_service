package com.saga.saga_pochotel_reservation_service.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HotelRepositoryIT {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void hotelRepositoryBeanExists() {
        assertTrue(applicationContext.containsBean("hotelRepository"));
    }

}