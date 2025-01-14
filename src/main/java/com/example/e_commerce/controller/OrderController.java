package com.example.e_commerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_commerce.Response;
import com.example.e_commerce.service.OrderService;

@RestController
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /********************************************************************************************** */

    @GetMapping("/order/{orderId}")
    public Response getOrderbyId(@PathVariable("orderId") Integer orderId) {
        return new Response(HttpStatus.OK, orderService.getOrderbyId(orderId), "Success");
    }

    /***********************************************************************************************/

    @GetMapping("/order/create/{addressId}")
    public Response createOrder(@PathVariable("addressId") Integer addressId) {
        orderService.createOrder(addressId);
        return new Response(HttpStatus.OK, null, "Success");
    }

    /************************************************************************************************/
    @GetMapping("/order/cancel/{orderId}")
    public Response cancelOrder(@PathVariable("orderId") Integer orderId) {
        orderService.cancelOrder(orderId);
        return new Response(HttpStatus.OK, null, "Success");
    }

    /*********************************************************************************************** */

    @GetMapping("/order/checkout/{orderId}")
    public Response checkout(@PathVariable("orderId") Integer orderId) {
        orderService.checkout(orderId);
        return new Response(HttpStatus.OK, null, "Success");
    }

    /*********************************************************************************************** */

}
