package com.saga.saga_poc__car_reservation_service.repository;

import com.saga.saga_poc__car_reservation_service.model.CarReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarReservationRepository extends JpaRepository<CarReservation, Long> {
}
