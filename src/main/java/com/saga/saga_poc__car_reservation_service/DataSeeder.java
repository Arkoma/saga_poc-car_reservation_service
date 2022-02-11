package com.saga.saga_poc__car_reservation_service;

import com.saga.saga_poc__car_reservation_service.model.Car;
import com.saga.saga_poc__car_reservation_service.repository.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CarRepository carRepository;

    public DataSeeder(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void run(String... args) {
        loadHotelData();
    }

    private void loadHotelData() {
        if (carRepository.count() == 0) {
            Car car1 = new Car();
            car1.setMake("Ford");
            car1.setModel("Model-T");
            car1.setMileage(78702L);
            Car car2 = new Car();
            car2.setMake("Tesla");
            car2.setModel("CyberTruck");
            car2.setMileage(78702L);
            Car car3 = new Car();
            car3.setMake("Porsche");
            car3.setModel("911");
            car3.setMileage(78702L);
            System.out.println("saving hotel " + carRepository.save(car1));
            System.out.println("saving hotel " + carRepository.save(car2));
            System.out.println("saving hotel " + carRepository.save(car3));
        }
    }
}
