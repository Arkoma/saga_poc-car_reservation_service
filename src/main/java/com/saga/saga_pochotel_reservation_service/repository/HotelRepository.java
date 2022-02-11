package com.saga.saga_pochotel_reservation_service.repository;

import com.saga.saga_pochotel_reservation_service.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Optional<Hotel> findByName(String name);
}
