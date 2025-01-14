package com.example.e_commerce.controller;

import java.io.IOException;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_commerce.Response;
import com.example.e_commerce.dto.AddAddressRequest;
import com.example.e_commerce.dto.UpdateProfileImage;
import com.example.e_commerce.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

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

    /**
     * @throws IOException
     * **********************************************************************************************
     */
    @GetMapping("/update-profile-image")
    public Response updateProfile(@ModelAttribute UpdateProfileImage updateProfileImage) throws IOException {
        userService.updateProfile(updateProfileImage);
        return new Response(HttpStatus.OK, null, "Success");

    }

    /*********************************************************************************************** */
    @PostMapping("/add-address")
    public Response addAddress(@RequestBody AddAddressRequest addAddressRequest) {
        userService.addAddress(addAddressRequest);
        return new Response(HttpStatus.OK, null, "Success");
    }

    /*********************************************************************************************** */
    @PostMapping("/add-phone")
    public Response addPhone(@Param("phone") Integer phone) {
        userService.addPhone(phone);
        return new Response(HttpStatus.OK, null, "Success");
    }

    /********************************************************************************************* */

    @PostMapping("/delete-phone/{phoneId}")
    public Response deletePhone(@PathVariable("phoneId") Integer phoneId) {
        userService.deletePhone(phoneId);
        return new Response(HttpStatus.OK, null, "Success");
    }

    /********************************************************************************************* */

    @PostMapping("/delete-address/{addressId}")
    public Response deleteAddress(@PathVariable("addressId") Integer addressId) {
        userService.deleteAddress(addressId);
        return new Response(HttpStatus.OK, null, "Success");
    }

    /**
     * @throws Exception *******************************************************************************************
     */

    @DeleteMapping("/delete-account")
    public Response deleteAccount() throws Exception {
        userService.deleteAccount();
        return new Response(HttpStatus.OK, null, "Success");
    }

}
