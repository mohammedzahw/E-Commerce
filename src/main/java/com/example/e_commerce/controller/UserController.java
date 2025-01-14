package com.example.e_commerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_commerce.Response;
import com.example.e_commerce.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /********************************************************************************************** */
    @GetMapping("/cart")
    public Response getCart() {
        return new Response(HttpStatus.OK, userService.getCart(), "Success");
    }

    /********************************************************************************************** */

    @GetMapping("/add-to-cart/{productId}")
    public Response addToCart(@PathVariable("productId") Integer productId) {
        userService.addToCart(productId);
        return new Response(HttpStatus.OK, null, "Success");
    }

    /****************************************************************************************************/
    @GetMapping("/remove-from-cart/{productId}")
    public Response removeFromCart(@PathVariable("productId") Integer productId) {
        userService.removeFromCart(productId);
        return new Response(HttpStatus.OK, null, "Success");
    }

    /************************************************************************************************ */

    @GetMapping("/clear-cart")
    public Response clearCart() {
        userService.clearCart();
        return new Response(HttpStatus.OK, null, "Success");
    }

    /************************************************************************************************ */

    @GetMapping("/add-to-wishlist/{productId}")
    public Response addToWishList(@PathVariable("productId") Integer productId) {
        userService.addToWishList(productId);
        return new Response(HttpStatus.OK, null, "Success");
    }

    /************************************************************************************************ */
    @GetMapping("/wishlist")
    public Response getWishList() {
        return new Response(HttpStatus.OK, userService.getWishList(), "Success");
    }

    /************************************************************************************************ */
    @GetMapping("/remove-from-wishlist/{productId}")
    public Response removeFromWishList(@PathVariable("productId") Integer productId) {
        userService.removeFromWishList(productId);
        return new Response(HttpStatus.OK, null, "Success");
    }

    /************************************************************************************************ */

}
