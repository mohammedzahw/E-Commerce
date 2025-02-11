package com.example.e_commerce.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_commerce.Response;
import com.example.e_commerce.dto.AddProductMedia;
import com.example.e_commerce.dto.CreateProductRequest;
import com.example.e_commerce.dto.UpdateProductRequest;
import com.example.e_commerce.dto.UpdateProfileImage;
import com.example.e_commerce.service.ProductService;
import com.example.e_commerce.service.VendorService;

@RequestMapping("/vendor")
@RestController
public class VendorController {

    private ProductService productService;
    private VendorService vendorService;

    public VendorController(ProductService productService, VendorService vendorService) {
        this.productService = productService;
        this.vendorService = vendorService;

    }

    /****************************************************************************************************/
    @GetMapping("/products/{vendorId}")
    public Response getProducts(@PathVariable("vendorId") Integer vendorId) {
        return new Response(HttpStatus.OK, productService.getProductsByVendor(vendorId), "Success");
    }

    /****************************************************************************************************/
    @PostMapping("/product/create")
    public Response createProduct(@RequestBody CreateProductRequest createProductRequest) {
        productService.createProduct(createProductRequest);
        return new Response(HttpStatus.OK, null, "Success");
    }

    /****************************************************************************************************/
    @PostMapping("/product/update")
    public Response updateProduct(@RequestBody UpdateProductRequest updateProductRequest) {
        productService.updateProduct(updateProductRequest);
        return new Response(HttpStatus.OK, null, "Success");
    }

    /**
     * @throws IOException
     ***************************************************************************************************/

    @PostMapping("/product/add-images")
    public Response addProductImage(@ModelAttribute AddProductMedia addProductMedia) throws IOException {
        // System.out.println(addProductMedia);
        productService.addProductImages(addProductMedia);
        return new Response(HttpStatus.OK, null, "Success");
    }

    /**
     * @throws IOException
     ***************************************************************************************************/

    @PostMapping("/product/add-videos")
    public Response addProductVideo(@ModelAttribute AddProductMedia addProductMedia) throws IOException {
        productService.addProductVideos(addProductMedia);
        return new Response(HttpStatus.OK, null, "Success");
    }

    /**
     * @throws Exception
     ************************************************************************************************/
    @DeleteMapping("/product/delete/{productId}")
    public Response deleteProduct(@PathVariable("productId") Integer productId) throws Exception {
        productService.deleteProduct(productId);
        return new Response(HttpStatus.OK, null, "Success");
    }

    /****************************************************************************************************/

    @GetMapping("/update-profile-image")
    public Response updateProfile(@ModelAttribute UpdateProfileImage updateProfileImage) throws IOException {
        vendorService.updateProfile(updateProfileImage);
        return new Response(HttpStatus.OK, null, "Success");

    }

}
