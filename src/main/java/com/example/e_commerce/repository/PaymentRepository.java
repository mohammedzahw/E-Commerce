package com.example.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.e_commerce.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
