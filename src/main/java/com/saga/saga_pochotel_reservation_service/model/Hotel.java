package com.saga.saga_pochotel_reservation_service.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Hotel {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String streetAddress;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;
    private String emailContact;
}
