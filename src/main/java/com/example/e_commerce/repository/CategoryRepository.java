package com.example.e_commerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.e_commerce.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    public Optional<Category> findByName(String name);

    @SuppressWarnings("null")
    public Optional<Category> findById(Integer id);

}
