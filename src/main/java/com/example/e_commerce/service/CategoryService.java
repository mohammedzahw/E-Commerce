package com.example.e_commerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.e_commerce.model.Category;
import com.example.e_commerce.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /********************************************************************************************* */

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}
