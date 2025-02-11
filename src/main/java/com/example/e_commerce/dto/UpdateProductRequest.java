package com.example.e_commerce.dto;

import java.util.List;

import lombok.Data;

@Data
public class UpdateProductRequest {
    private Integer id;
    private String title;
    private String description;
    private double price;
    private int quantity;
    private List<Integer> categories;
}
