package com.example.e_commerce.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.e_commerce.config.TokenUtil;
import com.example.e_commerce.exception.CustomException;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.model.User;
import com.example.e_commerce.repository.ProductRepository;
import com.example.e_commerce.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final TokenUtil tokenUtil;

    public UserService(UserRepository userRepository, ProductRepository productRepository, TokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.tokenUtil = tokenUtil;
        this.productRepository = productRepository;
    }

    /*********************************************************************************************** */
    @Transactional
    public void addToCart(Integer productId) {
        User user = userRepository.findById(tokenUtil.getUserId())
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        if (!productRepository.existsById(productId)) {
            throw new CustomException("Product not found", HttpStatus.NOT_FOUND);
        }
        userRepository.addToCart(user.getId(), productId);

    }

    /*********************************************************************************************** */
    @Transactional
    public void removeFromCart(Integer productId) {
        User user = userRepository.findById(productId)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        userRepository.removeFromCart(user.getId(), productId);
    }

    /*********************************************************************************************** */
    @Transactional
    public void clearCart() {
        userRepository.clearCart(tokenUtil.getUserId());
    }

    /*********************************************************************************************** */
    @Transactional
    public void addToWishList(Integer productId) {
        User user = userRepository.findById(productId)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        if (productRepository.existsById(productId)) {
            throw new CustomException("Product not found", HttpStatus.NOT_FOUND);
        }
        userRepository.addToWishList(user.getId(), productId);
    }

    /*********************************************************************************************** */
    @Transactional
    public void removeFromWishList(Integer productId) {
        User user = userRepository.findById(productId)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        userRepository.removeFromWishList(user.getId(), productId);
    }

    /*********************************************************************************************** */

    public List<Product> getCart() {
        return userRepository.getCart(tokenUtil.getUserId());
    }

    /*********************************************************************************************** */
    public List<Product> getWishList() {
        return userRepository.getWishList(tokenUtil.getUserId());
    }

}
