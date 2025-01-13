package com.example.e_commerce.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class VendorSignUpRequest {

    @Pattern(regexp = "^\\S*$", message = "Spaces are not allowed")
    private String name;

    @Email(message = "Invalid email address")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    private String password;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String street;
    private String houseNumber;

    private MultipartFile image;

}
