package com.saga.saga_poc__car_reservation_service.service;

import com.saga.saga_poc__car_reservation_service.model.Car;
import com.saga.saga_poc__car_reservation_service.model.CarReservation;
import com.saga.saga_poc__car_reservation_service.model.CarReservationRequest;
import com.saga.saga_poc__car_reservation_service.repository.CarRepository;
import com.saga.saga_poc__car_reservation_service.repository.CarReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarReservationServiceTest {

    @InjectMocks
    private CarReservationService underTest;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarReservationRepository carReservationRepository;

    private CarReservation carReservation;

    private Long carReservationId;

    @BeforeEach
    void setup() {
        carReservationId = 3L;
        carReservation = new CarReservation();
        carReservation.setId(carReservationId);
    }

    @Test
    void makeReservationSavesCarPassedIn() throws ParseException {
        String carMake = "Ford";
        String carModel = "Model-T";
        Long carId = 1L;
        Long reservationId = 2L;
        Car car = new Car();
        car.setId(carId);
        car.setMake(carMake);
        car.setModel(carModel);
        when(carRepository.findByMakeAndModel(anyString(), anyString())).thenReturn(Optional.of(car));
        when (carReservationRepository.save(any(CarReservation.class))).thenReturn(carReservation);
        final String agencyName = "Hertz";
        final Date checkinDate = new SimpleDateFormat("d MMM yyyy").parse("9 Feb 2022");
        final Date checkoutDate = new SimpleDateFormat("dd MMM yyyy").parse("12 Feb 2022");
        CarReservationRequest request = CarReservationRequest.builder()
                .reservationId(reservationId)
                .car(car)
                .agency(agencyName)
                .checkinDate(checkinDate)
                .checkoutDate(checkoutDate)
                .build();
        CarReservation actual = underTest.makeReservation(request);
        assertAll(() -> {
            assertEquals(carReservationId, actual.getId());
            verify(carRepository, times(1)).findByMakeAndModel(carMake, carModel);
            verify(carReservationRepository, times(1)).save(any(CarReservation.class));
        });
    }

    @Test
    void cancelReservationDeletesEntity() {
        underTest.cancelReservation(1L);
        verify(carReservationRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testGetReservationByIdCallsFindById() {
        when(carReservationRepository.findById(anyLong())).thenReturn(Optional.of(carReservation));
        CarReservation actual = underTest.getReservationById(1L);
        verify(carReservationRepository, times(1)).findById(anyLong());
        assertEquals(carReservation.getId(), actual.getId());
    }

    @Test
    void getAllReservationsCallsFindAll() {
        underTest.getAllReservations();
        verify(carReservationRepository, times(1)).findAll();
    }
}