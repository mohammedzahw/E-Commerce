package com.example.e_commerce.service;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.e_commerce.config.TokenUtil;
import com.example.e_commerce.dto.AddProductMedia;
import com.example.e_commerce.dto.CreateProductRequest;
import com.example.e_commerce.dto.MediaDto;
import com.example.e_commerce.dto.UpdateProductRequest;
import com.example.e_commerce.mapper.ProductMapper;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.model.ProductImage;
import com.example.e_commerce.model.ProductVideo;
import com.example.e_commerce.repository.ProductRepository;
import com.example.e_commerce.repository.VendorRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private ProductMapper productMapper;
    private MediaService mediaService;
    private TokenUtil tokenUtil;

    public ProductService(ProductRepository productRepository, TokenUtil tokenUtil,
            MediaService mediaService, ProductMapper productMapper) {
        this.productRepository = productRepository;
        ;
        this.tokenUtil = tokenUtil;
        this.productMapper = productMapper;
        this.mediaService = mediaService;
    }

    /********************************************************************************************** */
    public List<Product> getProductsByCategory(Long catId, int page) {
        return productRepository.findProductsByCategoryId(catId, PageRequest.of(page, 10));
    }

    /********************************************************************************************** */
    public List<Product> getProductsByVendor(Long vendorId) {
        return productRepository.findProductsByVendorId(vendorId);
    }

    /********************************************************************************************** */
    public Product createProduct(CreateProductRequest createProductRequest) {

        Product product = productMapper.toProduct(createProductRequest);
        return productRepository.save(product);
    }

    /********************************************************************************************** */

    public void updateProduct(UpdateProductRequest updateProductRequest) {

        Product product = productRepository.findById(updateProductRequest.getId()).orElseThrow(
                () -> new RuntimeException("Product not found"));
        product = productMapper.updateProduct(updateProductRequest, product);
        productRepository.save(product);

    }

    /**
     * @throws IOException
     *                     ****************************************************************************
     */
    public void addProductImages(AddProductMedia addProductMedia) throws IOException {
        Product product = productRepository.findById(addProductMedia.getProductId()).orElseThrow(
                () -> new RuntimeException("Product not found"));

        for (MultipartFile file : addProductMedia.getFiles()) {

            MediaDto mediaDto = mediaService.uploadImage(file, null, "product" + product.getId());
            ProductImage productImage = new ProductImage();
            productImage.setUrl(mediaDto.getUrl());
            productImage.setId(mediaDto.getId());
            productImage.setProduct(product);
            product.getImages().add(productImage);
        }
        productRepository.save(product);
    }

    /**
     * @throws IOException
     *                     ****************************************************************************
     */
    public void addProductVideos(AddProductMedia addProductMedia) throws IOException {
        Product product = productRepository.findById(addProductMedia.getProductId()).orElseThrow(
                () -> new RuntimeException("Product not found"));

        for (MultipartFile file : addProductMedia.getFiles()) {

            MediaDto mediaDto = mediaService.uploadVideo(file, null, "product" + product.getId());
            ProductVideo productVideo = new ProductVideo();
            productVideo.setUrl(mediaDto.getUrl());
            productVideo.setId(mediaDto.getId());
            productVideo.setProduct(product);
            product.getVideos().add(productVideo);
        }
        productRepository.save(product);
    }

    /*******************************************************************************************
     * 
     * @param productId
     * @throws Exception
     */
    public void deleteProduct(Long productId) throws Exception {
        System.out.println("productId : " + productId);
        System.out.println("userId : " + tokenUtil.getUserId());
        if (!productRepository.isVendorProductOwner(tokenUtil.getUserId(), (productId))) {
            throw new RuntimeException("You are not authorized to delete this product");
        }

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("Product not found"));

        mediaService.deleteByFolder("product" + product.getId());

        productRepository.delete(product);
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
    }

}
