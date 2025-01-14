package com.example.e_commerce.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE)
    private Integer id;
    private LocalDateTime orderDate;
    private DileveryStatus status;
    private double totalAmount;
    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @ToString.Exclude
    private Address address;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @ToString.Exclude
    private User user;

    @ManyToMany(fetch = jakarta.persistence.FetchType.LAZY)
    @ToString.Exclude
    private List<Product> products;

    public Order(Address address, User user, List<Product> products) {
        this.orderDate = LocalDateTime.now();
        this.status = DileveryStatus.PENDING;
        this.totalAmount = 0;
        products.forEach(product -> this.totalAmount += product.getPrice());
        this.address = address;
        this.user = user;
        this.products = products;
    }
}
