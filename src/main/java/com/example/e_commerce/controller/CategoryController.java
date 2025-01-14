package com.example.e_commerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_commerce.Response;
import com.example.e_commerce.service.CategoryService;

@RestController
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**************************************************************************************************/

    @GetMapping("/categories")
    public Response getCategories() {
        return new Response(HttpStatus.OK, categoryService.getCategories(), "Success");
    }

}
