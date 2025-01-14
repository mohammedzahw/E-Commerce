package com.example.e_commerce.controller;

import org.hibernate.sql.Update;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_commerce.Response;
import com.example.e_commerce.dto.UpdateOrderStatusRequest;
import com.example.e_commerce.model.Order;
import com.example.e_commerce.service.OrderService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RequestMapping("/admin")
@RestController
public class AdminController {

    private OrderService orderService;

    public AdminController(OrderService orderService) {
        this.orderService = orderService;
    }

    /*********************************************************************************************/
    @GetMapping("/dashboard")
    public String getDashboard() {
        return "Admin Dashboard";
    }

    /********************************************************************************************* */
    @PostMapping("/order/update")
    public Response updateOrder(@RequestBody UpdateOrderStatusRequest updateOrderStatusRequest) {
        orderService.updateOrderStatus(updateOrderStatusRequest);
        return new Response(HttpStatus.OK, null, "Success");
    }

}
