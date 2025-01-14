package com.example.e_commerce.model;

import com.example.e_commerce.dto.UserSignUpRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;

import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE)
    private Integer id;
    private String city;
    private String state;
    private String country;
    private Integer zipCode;
    private String street;
    private String houseNumber;
    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private User user;

    public Address(UserSignUpRequest signUpRequest) {
        this.city = signUpRequest.getCity();
        this.state = signUpRequest.getState();
        this.country = signUpRequest.getCountry();
        this.zipCode = signUpRequest.getZipCode();
        this.street = signUpRequest.getStreet();
        this.houseNumber = signUpRequest.getHouseNumber();
    }

}
