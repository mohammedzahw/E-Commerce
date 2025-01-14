package com.example.e_commerce.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String title;
    private String description;
    private double price;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private Vendor vendor;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    @ToString.Exclude
    @JsonIgnore
    private List<Category> categories;

    @OneToMany(fetch = FetchType.EAGER, cascade = { jakarta.persistence.CascadeType.ALL }, mappedBy = "product")
    @ToString.Exclude
    @JsonIgnore
    private List<ProductImage> images;

    @OneToMany(fetch = FetchType.EAGER, cascade = { jakarta.persistence.CascadeType.ALL }, mappedBy = "product")
    @ToString.Exclude
    @JsonIgnore
    private List<ProductVideo> videos;

}
