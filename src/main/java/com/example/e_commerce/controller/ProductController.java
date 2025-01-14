package com.example.e_commerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_commerce.Response;
import com.example.e_commerce.service.ProductService;

@RestController
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /****************************************************************************************************/
    @GetMapping("/products/{vendorId}")
    public Response getProducts(@PathVariable("vendorId") Long vendorId) {
        return new Response(HttpStatus.OK, productService.getProductsByVendor(vendorId), "Success");
    }

    /****************************************************************************************************/

    @GetMapping("/products/{catId}/{page}")
    public Response getProducts(@PathVariable("catId") Long catId,
            @PathVariable("page") int page) {
        return new Response(HttpStatus.OK, productService.getProductsByCategory(catId, page), "Success");
    }
    /****************************************************************************************************/
}
