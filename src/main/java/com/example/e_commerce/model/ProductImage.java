package com.example.e_commerce.model;

import jakarta.persistence.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class ProductImage {

    @Id
    private String Id;
    private String url;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Product product;
}
