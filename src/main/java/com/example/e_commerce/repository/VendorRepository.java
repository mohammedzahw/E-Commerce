package com.example.e_commerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.e_commerce.model.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {
    Optional<Vendor> findByEmail(String email);

}
