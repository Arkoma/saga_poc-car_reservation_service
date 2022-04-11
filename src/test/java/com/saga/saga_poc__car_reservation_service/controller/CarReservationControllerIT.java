package com.saga.saga_poc__car_reservation_service.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saga.saga_poc__car_reservation_service.model.Car;
import com.saga.saga_poc__car_reservation_service.model.CarReservation;
import com.saga.saga_poc__car_reservation_service.model.CarReservationRequest;
import com.saga.saga_poc__car_reservation_service.model.StatusEnum;
import com.saga.saga_poc__car_reservation_service.repository.CarRepository;
import com.saga.saga_poc__car_reservation_service.repository.CarReservationRepository;
import com.saga.saga_poc__car_reservation_service.service.CarReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
class CarReservationControllerIT {

    private final WebApplicationContext webApplicationContext;
    private final CarReservationService carReservationService;
    private final CarRepository carRepository;
    private final CarReservationRepository carReservationRepository;
    private final CarReservationController underTest;

    @Autowired
    public CarReservationControllerIT(WebApplicationContext webApplicationContext,
                                      CarReservationService carReservationService,
                                      CarReservationController carReservationController,
                                      CarRepository carRepository,
                                      CarReservationRepository carReservationRepository) throws ParseException {
        this.webApplicationContext = webApplicationContext;
        this.carReservationService = carReservationService;
        this.underTest = carReservationController;
        this.carRepository = carRepository;
        this.carReservationRepository = carReservationRepository;
    }

    private MockMvc mockMvc;
    private CarReservationRequest carReservationRequest;
    private Car car;
    private final Long reservationId = 1L;
    private final String agencyName = "Hertz";
    private final Date checkinDate = new SimpleDateFormat("d MMM yyyy").parse("9 Feb 2022");
    private final Date checkoutDate = new SimpleDateFormat("dd MMM yyyy").parse("12 Feb 2022");
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        final String make = "Ford";
        final String model = "Model-T";
        car = new Car();
        car.setMake(make);
        car.setModel(model);
        this.carRepository.deleteAll();
        car = this.carRepository.save(car);
        carReservationRequest = CarReservationRequest.builder()
                .reservationId(reservationId)
                .carMake(make)
                .carModel(model)
                .agency(agencyName)
                .checkinDate(checkinDate)
                .checkoutDate(checkoutDate)
                .build();
        this.carReservationRepository.deleteAll();
    }

    @Test
    void hotelReservationControllerExistsAsABean() {
        assertTrue(webApplicationContext.containsBean("carReservationController"));
    }

    @Test
    void hotelReservationServiceIsInjectedInTheController() {
        CarReservationService injectedCarReservationService =(CarReservationService) ReflectionTestUtils.getField(underTest, "carReservationService");
        assertSame(carReservationService, injectedCarReservationService);
    }

    @Test
    void makeReservationEndpointExists() throws Exception {
        String json = mapper.writeValueAsString(this.carReservationRequest);
        this.mockMvc.perform(post("/reservation").contentType(APPLICATION_JSON_VALUE).content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void makeReservationEndpointReturnsReservation() throws Exception {
        final MvcResult result = this.makeReservation(reservationId);
        String responseJson = result.getResponse().getContentAsString();
        CarReservation actualResponse = mapper.readValue(responseJson, CarReservation.class);
        assertAll(() -> {
            assertEquals(StatusEnum.RESERVED, actualResponse.getStatus());
            assertEquals(car.getId(), actualResponse.getCarId());
            assertEquals(car.getMake(), actualResponse.getCarMake());
            assertEquals(car.getModel(), actualResponse.getCarModel());
            assertEquals(this.reservationId, actualResponse.getReservationId());
            assertEquals(this.checkinDate, actualResponse.getCheckinDate());
            assertEquals(this.checkoutDate, actualResponse.getCheckoutDate());
            assertEquals(this.agencyName, actualResponse.getAgency());
                }
        );
    }

    private MvcResult makeReservation(Long reservationId) throws Exception {
        this.carReservationRequest.setReservationId(reservationId);
        String json = mapper.writeValueAsString(this.carReservationRequest);
        return this.mockMvc.perform(post("/reservation").contentType(APPLICATION_JSON_VALUE).content(json))
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(APPLICATION_JSON_VALUE)
                )
                .andReturn();
    }

    @Test
    @Transactional
    void makeReservationEndpointSavesReservation() throws Exception {
        final MvcResult result = this.makeReservation(reservationId);
        String responseJson = result.getResponse().getContentAsString();
        CarReservation actualResponse = mapper.readValue(responseJson, CarReservation.class);
        CarReservation actualEntity = this.carReservationRepository.getById(actualResponse.getId());
        assertAll(() -> {
            assertEquals(StatusEnum.RESERVED ,actualEntity.getStatus());
            assertEquals(car.getId(), actualEntity.getCarId());
            assertEquals(this.reservationId, actualEntity.getReservationId());
            assertEquals(this.checkinDate, actualEntity.getCheckinDate());
            assertEquals(this.checkoutDate, actualEntity.getCheckoutDate());
            assertEquals(this.agencyName, actualEntity.getAgency());
                }
        );
    }

    @Test
    void cancelReservationEndpointExists() throws Exception {
        final MvcResult result = this.makeReservation(reservationId);
        String responseJson = result.getResponse().getContentAsString();
        CarReservation reservation = mapper.readValue(responseJson, CarReservation.class);
        Long id = reservation.getId();
        MvcResult mvcResult = this.mockMvc.perform(delete("/reservation/" + id))
                .andExpectAll(
                        status().isNoContent(),
                        content().contentType(TEXT_PLAIN + ";charset=UTF-8")
                ).andReturn();
        String message = mvcResult.getResponse().getContentAsString();
        assertEquals("Car reservation cancelled", message);
    }

    @Test
    @Transactional
    void cancelReservationEndpointRemovesReservation() throws Exception {
        final MvcResult result = this.makeReservation(reservationId);
        String responseJson = result.getResponse().getContentAsString();
        CarReservation reservation = mapper.readValue(responseJson, CarReservation.class);
        Long id = reservation.getId();
        CarReservation reservationFromDb = this.carReservationRepository.getById(id);
        assertEquals(id, reservationFromDb.getId());
        this.mockMvc.perform(delete("/reservation/" + id))
                .andExpect(status().isNoContent());
        reservationFromDb = this.carReservationRepository.findById(id).orElse(null);
        assertNull(reservationFromDb);
    }
    
    @Test
    void getReservationEndpointExists() throws Exception {
        final MvcResult result = this.makeReservation(reservationId);
        String responseJson = result.getResponse().getContentAsString();
        CarReservation reservation = mapper.readValue(responseJson, CarReservation.class);
        Long id = reservation.getId();
        final MvcResult foundResult = this.mockMvc.perform(get("/reservation/" + id))
            .andExpectAll(
                    status().isOk(),
                    content().contentType(APPLICATION_JSON_VALUE))
            .andReturn();
        String foundResponseJson = foundResult.getResponse().getContentAsString();
        CarReservation foundReservation = mapper.readValue(foundResponseJson, CarReservation.class);
        assertAll(() -> {
                    assertEquals(StatusEnum.RESERVED ,foundReservation.getStatus());
                    assertEquals(this.car.getId(), foundReservation.getCarId());
                    assertEquals(this.reservationId, foundReservation.getReservationId());
                    assertEquals(this.checkinDate, foundReservation.getCheckinDate());
                    assertEquals(this.checkoutDate, foundReservation.getCheckoutDate());
                    assertEquals(this.agencyName, foundReservation.getAgency());
                }
        );
    }

    @Test
    void getReservationReturnsNotFoundIfReservationDoesNotExist() throws Exception {
        this.mockMvc.perform(get("/reservation/" + 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAll() throws Exception {
        this.makeReservation(reservationId);
        final Long newReservationId = 123L;
        this.makeReservation(newReservationId);
        final MvcResult foundResult = this.mockMvc.perform(get("/reservations"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(APPLICATION_JSON_VALUE))
                .andReturn();
        String foundResponseJson = foundResult.getResponse().getContentAsString();
        List<CarReservation> foundReservations = mapper.readValue(foundResponseJson, new TypeReference<>(){});
        assertAll(() -> {
                    assertEquals(StatusEnum.RESERVED ,foundReservations.get(0).getStatus());
                    assertEquals(this.car.getId(), foundReservations.get(0).getCarId());
                    assertEquals(this.reservationId, foundReservations.get(0).getReservationId());
                    assertEquals(this.checkinDate, foundReservations.get(0).getCheckinDate());
                    assertEquals(this.checkoutDate, foundReservations.get(0).getCheckoutDate());
                    assertEquals(this.agencyName, foundReservations.get(0).getAgency());
                    assertEquals(2, foundReservations.size());
                }
        );
    }

}

