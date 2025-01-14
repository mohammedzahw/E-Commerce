package com.example.e_commerce.mapper;

import org.springframework.stereotype.Component;

import com.example.e_commerce.dto.VendorSignUpRequest;

import com.example.e_commerce.model.Vendor;

import lombok.Data;

@Data
@Component
public class VendorMapper {

    public Vendor toEntity(VendorSignUpRequest signUpRequest) {
        if (signUpRequest == null) {
            return null;
        }
        Vendor vendor = new Vendor();
        vendor.setEmail(signUpRequest.getEmail());
        vendor.setName(signUpRequest.getName());
        vendor.setPassword(signUpRequest.getPassword());
        vendor.setActive(false);

        return vendor;
    }
}
