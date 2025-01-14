package com.example.e_commerce.dto;

import lombok.Data;

@Data
public class AddAddressRequest {
    private String city;
    private String state;
    private String country;
    private Integer zipCode;
    private String street;
    private Integer houseNumber;
}
