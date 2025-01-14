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

    @GetMapping("/products/{catId}/{page}")
    public Response getProducts(@PathVariable("catId") Integer catId,
            @PathVariable("page") int page) {
        return new Response(HttpStatus.OK, productService.getProductsByCategory(catId, page), "Success");
    }

    /****************************************************************************************************/

    @GetMapping("/product/{productId}")
    public Response getProduct(@PathVariable("productId") Integer productId) {
        return new Response(HttpStatus.OK, productService.getProduct(productId), "Success");
    }

    /****************************************************************************************************/
    @GetMapping("/products/search/{keyword}")
    public Response searchProducts(@PathVariable("keyword") String keyword) {
        return new Response(HttpStatus.OK, productService.searchProducts(keyword), "Success");
    }
    /************************************************************************************************ */
}
