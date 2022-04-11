package com.saga.saga_poc__car_reservation_service.service;

import com.saga.saga_poc__car_reservation_service.model.Car;
import com.saga.saga_poc__car_reservation_service.model.CarReservation;
import com.saga.saga_poc__car_reservation_service.model.CarReservationRequest;
import com.saga.saga_poc__car_reservation_service.model.StatusEnum;
import com.saga.saga_poc__car_reservation_service.repository.CarRepository;
import com.saga.saga_poc__car_reservation_service.repository.CarReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CarReservationServiceTest {

    @InjectMocks
    private CarReservationService underTest;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarReservationRepository carReservationRepository;

    @Captor
    private ArgumentCaptor<CarReservation> carReservationArgumentCaptor;

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
        final String carMake = "Ford";
        final String carModel = "Model-T";
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
                .carMake(carMake)
                .carModel(carModel)
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

    @Test
    void statusSetToCancelledIfCarDoesNotExist() throws ParseException {
        final String carMake = "Ferd";
        final String carModel = "Mrdel-T";
        Long carId = 1L;
        Long reservationId = 2L;
        Car car = new Car();
        car.setId(carId);
        car.setMake(carMake);
        car.setModel(carModel);
        when(carRepository.findByMakeAndModel(anyString(), anyString())).thenReturn(Optional.empty());
        final String agencyName = "Hertz";
        final Date checkinDate = new SimpleDateFormat("d MMM yyyy").parse("9 Feb 2022");
        final Date checkoutDate = new SimpleDateFormat("dd MMM yyyy").parse("12 Feb 2022");
        CarReservationRequest request = CarReservationRequest.builder()
                .reservationId(reservationId)
                .carMake(carMake)
                .carModel(carModel)
                .agency(agencyName)
                .checkinDate(checkinDate)
                .checkoutDate(checkoutDate)
                .build();
        underTest.makeReservation(request);
        assertAll(() -> {
            verify(carRepository, times(1)).findByMakeAndModel(carMake, carModel);
            verify(carReservationRepository, times(1)).save(carReservationArgumentCaptor.capture());
            CarReservation actual = carReservationArgumentCaptor.getValue();
            assertEquals(StatusEnum.CANCELLED, actual.getStatus());
        });
    }

    @Test
    void ifCarReservationFoundByReservationIdNoNewReservationSaved() {
        final Long reservationId = 1L;
        CarReservation reservationToFind = new CarReservation();
        reservationToFind.setId(123L);
        reservationToFind.setReservationId(reservationId);
        when(carReservationRepository.findByReservationId(1L)).thenReturn(reservationToFind);
        CarReservationRequest carReservationRequest = CarReservationRequest.builder()
                .reservationId(reservationId)
                .build();
        CarReservation actual = underTest.makeReservation(carReservationRequest);
        verify(carReservationRepository, times(0)).save(any(CarReservation.class));
        assertEquals(reservationToFind.getId(), actual.getId());
    }
}