package com.example.e_commerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.e_commerce.dto.CategoryProducts;
import com.example.e_commerce.model.Category;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.repository.CategoryRepository;
import com.example.e_commerce.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /********************************************************************************************** */
    public List<Product> getProductsByCategory(Long catId, int page) {
        return productRepository.findProductsByCategoryId(catId, PageRequest.of(page, 10));
    }

    /********************************************************************************************** */
    public List<Product> getProductsByVendor(Long vendorId) {
        return productRepository.findProductsByVendorId(vendorId);
    }

}
