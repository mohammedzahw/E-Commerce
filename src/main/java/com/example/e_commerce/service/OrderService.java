package com.example.e_commerce.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.e_commerce.dto.UpdateOrderStatusRequest;
import com.example.e_commerce.model.Order;
import com.example.e_commerce.model.User;
import com.example.e_commerce.repository.OrderRepository;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private SimpMessagingTemplate messagingTemplate;

    public OrderService(OrderRepository orderRepository, SimpMessagingTemplate messagingTemplate) {
        this.orderRepository = orderRepository;
        this.messagingTemplate = messagingTemplate;
    }

    /**************************************************************************************************** */
    public void updateOrderStatus(UpdateOrderStatusRequest updateOrderStatusRequest) {
        Order order = orderRepository.findById(updateOrderStatusRequest.getOrderId()).orElseThrow(
                () -> new RuntimeException("Order not found"));
        order.setStatus(updateOrderStatusRequest.getStatus());
        orderRepository.save(order);
        User user = orderRepository.findOrderUser(order.getId()).orElseThrow(
                () -> new RuntimeException("User not found"));
        String message = "Order status updated to " + updateOrderStatusRequest.getStatus();
        messagingTemplate.convertAndSend("/topic/notifications/" + user.getId(),
                message);

    }

}
