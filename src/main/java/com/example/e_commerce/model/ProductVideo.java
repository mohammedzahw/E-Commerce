package com.example.e_commerce.model;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class ProductVideo {
    @Id
    private String Id;
    private String url;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Product product;
}
