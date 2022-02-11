package com.saga.saga_pochotel_reservation_service.repository;

import com.saga.saga_pochotel_reservation_service.model.HotelReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelReservationRepository extends JpaRepository<HotelReservation, Long> {
}
