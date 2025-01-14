package com.example.e_commerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequest {

    @Email(message = "Invalid email address")
    @NotEmpty(message = "Email cannot be empty")
    public String email;
    public String password;
    private String role;

}
