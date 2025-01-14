package com.example.e_commerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @GetMapping("/order/{orderId}")
    public String getOrderbyId(@PathVariable("orderId") Integer orderId) {
        return "Orders";
    }

    /***********************************************************************************************/

    @GetMapping("/orders")
    public String getOrderbyUserId() {
        return "Orders";
    }
    /************************************************************************************************/

    /*********************************************************************************************** */

}
