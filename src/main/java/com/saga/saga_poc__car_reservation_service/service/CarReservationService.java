package com.saga.saga_poc__car_reservation_service.service;

import com.saga.saga_poc__car_reservation_service.model.Car;
import com.saga.saga_poc__car_reservation_service.model.CarReservation;
import com.saga.saga_poc__car_reservation_service.model.CarReservationRequest;
import com.saga.saga_poc__car_reservation_service.model.StatusEnum;
import com.saga.saga_poc__car_reservation_service.repository.CarRepository;
import com.saga.saga_poc__car_reservation_service.repository.CarReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CarReservationService {

    private final CarRepository carRepository;
    private final CarReservationRepository carReservationRepository;

    public CarReservationService(CarRepository carRepository, CarReservationRepository carReservationRepository) {
        this.carRepository = carRepository;
        this.carReservationRepository = carReservationRepository;
    }

    public CarReservation makeReservation(CarReservationRequest request) throws NoSuchElementException{
        Car car = carRepository.findByMakeAndModel(request.getCarMake(), request.getCarModel()).orElseThrow();
        CarReservation carReservation = new CarReservation();
        carReservation.setCarId(car.getId());
        carReservation.setCarMake(request.getCarMake());
        carReservation.setCarModel(request.getCarModel());
        carReservation.setReservationId(request.getReservationId());
        carReservation.setCheckinDate(request.getCheckinDate());
        carReservation.setCheckoutDate(request.getCheckoutDate());
        carReservation.setAgency(request.getAgency());
        try {
            carReservation.setStatus(StatusEnum.RESERVED);
            carReservation = carReservationRepository.save(carReservation);
        } catch (Exception e) {
            carReservation.setStatus(StatusEnum.CANCELLED);
            carReservation = carReservationRepository.save(carReservation);
        }
        return carReservation;
    }

    public void cancelReservation(Long id) {
        this.carReservationRepository.deleteById(id);
    }

    public CarReservation getReservationById(Long id) {
        return this.carReservationRepository.findById(id).orElse(null);
    }

    public List<CarReservation> getAllReservations() {
        return this.carReservationRepository.findAll();
    }
}
