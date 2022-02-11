package com.saga.saga_pochotel_reservation_service.service;

import com.saga.saga_pochotel_reservation_service.model.Hotel;
import com.saga.saga_pochotel_reservation_service.repository.HotelRepository;
import com.saga.saga_pochotel_reservation_service.repository.HotelReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HotelReservationServiceIT {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private HotelReservationService underTest;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelReservationRepository hotelReservationRepository;

    @Test
    void hotelReservationServiceBeanGetsCreated() {
        assertTrue(applicationContext.containsBean("hotelReservationService"));
    }

    @Test
    void hotelReservationServiceContainsHotelRepository() {
        HotelRepository injectedHotelRepository = (HotelRepository) ReflectionTestUtils.getField(underTest, "hotelRepository");
        assertSame(hotelRepository, injectedHotelRepository);
    }

    @Test
    void hotelReservationServiceContainsHotelReservationRepository() {
        HotelReservationRepository injectedHotelReservationRepository =(HotelReservationRepository) ReflectionTestUtils.getField(underTest, "hotelReservationRepository");
        assertSame(hotelReservationRepository, injectedHotelReservationRepository);
    }

}