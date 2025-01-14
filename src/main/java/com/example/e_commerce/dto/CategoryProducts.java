package com.example.e_commerce.dto;

import lombok.Data;

import java.util.List;

import com.example.e_commerce.model.Product;

@Data
public class CategoryProducts {
    private Long id;
    private String name;
    private List<Product> products;

    public CategoryProducts(Long id, String name, List<Product> products) {

        this.id = id;
        this.name = name;
        this.products = products;
    }

}
