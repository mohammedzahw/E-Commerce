package com.example.e_commerce.model;

import java.time.LocalDateTime;

import jakarta.persistence.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
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

    public Payment(String method, String currency, Order order, User user) {
        this.method = method;
        this.currency = currency;
        this.order = order;
        this.user = user;
        this.date = LocalDateTime.now();
        this.amount = order.getTotalAmount();
    }

}
