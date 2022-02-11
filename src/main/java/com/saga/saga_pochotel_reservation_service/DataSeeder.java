package com.saga.saga_pochotel_reservation_service;

import com.saga.saga_pochotel_reservation_service.model.Hotel;
import com.saga.saga_pochotel_reservation_service.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final HotelRepository hotelRepository;

    public DataSeeder(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadHotelData();
    }

    private void loadHotelData() {
        if (hotelRepository.count() == 0) {
            Hotel hotel1 = new Hotel();
            hotel1.setName("Holiday Inn");
            hotel1.setStreetAddress("42 McVernon st");
            hotel1.setCity("Fort Worth");
            hotel1.setState("TX");
            hotel1.setZip("78702");
            hotel1.setEmailContact("customerService@holidayInn.com");
            hotel1.setPhoneNumber("555-555-1212");
            Hotel hotel2 = new Hotel();
            hotel2.setName("Dallas Suites");
            hotel2.setStreetAddress("33 Highland Park Ave");
            hotel2.setCity("Dallas");
            hotel2.setState("TX");
            hotel2.setZip("78702");
            hotel2.setEmailContact("customerService@DallasSuites.com");
            hotel2.setPhoneNumber("555-555-1212");
            Hotel hotel3 = new Hotel();
            hotel3.setName("Austin Comfort stays");
            hotel3.setStreetAddress("83 Thomas Corner");
            hotel3.setCity("Austin");
            hotel3.setState("TX");
            hotel3.setZip("78702");
            hotel3.setEmailContact("customerService@asc.com");
            hotel3.setPhoneNumber("555-555-1212");
            System.out.println("saving hotel " + hotelRepository.save(hotel1));
            System.out.println("saving hotel " + hotelRepository.save(hotel2));
            System.out.println("saving hotel " + hotelRepository.save(hotel3));
        }
    }
}
