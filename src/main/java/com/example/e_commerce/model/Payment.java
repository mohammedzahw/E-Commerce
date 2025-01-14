package com.example.e_commerce.model;

import java.time.LocalDateTime;

import jakarta.persistence.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE)
    private Integer id;
    private String method;
    private LocalDateTime date;
    private double amount;
    private String currency;

    @OneToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @ToString.Exclude
    private Order order;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @ToString.Exclude
    private User user;

}
