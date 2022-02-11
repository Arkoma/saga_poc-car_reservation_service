package com.saga.saga_poc__car_reservation_service.repository;

import com.saga.saga_poc__car_reservation_service.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findByMakeAndModel(String make, String model);
}
