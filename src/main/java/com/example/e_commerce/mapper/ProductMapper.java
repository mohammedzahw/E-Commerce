package com.example.e_commerce.mapper;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.sql.Update;
import org.springframework.stereotype.Component;

import com.example.e_commerce.dto.CreateProductRequest;
import com.example.e_commerce.dto.UpdateProductRequest;
import com.example.e_commerce.model.Category;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.repository.CategoryRepository;

import lombok.Data;

@Data
@Component
public class ProductMapper {

    private CategoryRepository categoryRepository;

    public ProductMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /******************************************************************************* */

    public Product toProduct(CreateProductRequest productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        List<Category> categories = new ArrayList<>();
        for (Integer catId : productDto.getCategories()) {

            Category category = categoryRepository.findById(catId).orElse(null);
            if (category != null) {
                categories.add(category);
            }

        }
        product.setCategories(categories);
        return product;
    }

    /********************************************************************************************* */

    public Product updateProduct(UpdateProductRequest productDto, Product product) {

        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        List<Category> categories = new ArrayList<>();
        for (Integer catId : productDto.getCategories()) {

            Category category = categoryRepository.findById(catId).orElse(null);
            if (category != null) {
                categories.add(category);
            }

        }
        product.setCategories(categories);
        return product;
    }
    /********************************************************************************************* */

}
