package com.example.e_commerce.service;

import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.e_commerce.config.TokenUtil;
import com.example.e_commerce.dto.UpdateOrderStatusRequest;
import com.example.e_commerce.model.Address;
import com.example.e_commerce.model.Admin;
import com.example.e_commerce.model.DileveryStatus;
import com.example.e_commerce.model.Order;
import com.example.e_commerce.model.Payment;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.model.User;
import com.example.e_commerce.repository.AddressRepository;
import com.example.e_commerce.repository.AdminRepository;
import com.example.e_commerce.repository.OrderRepository;
import com.example.e_commerce.repository.PaymentRepository;
import com.example.e_commerce.repository.UserRepository;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private SimpMessagingTemplate messagingTemplate;
    private AdminRepository adminRepository;
    private AddressRepository addressRepository;
    private TokenUtil tokenUtil;
    private UserRepository userRepository;
    private PaymentRepository paymentRepository;

    public OrderService(OrderRepository orderRepository, PaymentRepository paymentRepository,
            AddressRepository addressRepository,
            UserRepository userRepository, AdminRepository adminRepository,
            TokenUtil tokenUtil,
            SimpMessagingTemplate messagingTemplate) {

        this.paymentRepository = paymentRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.tokenUtil = tokenUtil;
        this.adminRepository = adminRepository;
        this.messagingTemplate = messagingTemplate;
    }

    /**************************************************************************************************** */
    public void updateOrderStatus(UpdateOrderStatusRequest updateOrderStatusRequest) {

        if (!adminRepository.exisexistsById(tokenUtil.getUserId()))
            throw new RuntimeException("Admin not found");

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

    /************************************************************************************************ */
    public Order getOrderbyId(Integer orderId) {
        if (tokenUtil.getRole().equals("ROLE_ADMIN") || (tokenUtil.getRole().equals("ROLE_USER")
                && orderRepository.isUserIsOwner(tokenUtil.getUserId(), orderId)))
            return orderRepository.findById(orderId).orElseThrow(
                    () -> new RuntimeException("Order not found"));
        else
            throw new RuntimeException("You are not authorized to view this order");
    }

    /************************************************************************************************ */

    public void createOrder(Integer addressId) {
        List<Product> cart = userRepository.getCart(tokenUtil.getUserId());
        Address address = addressRepository.findById(addressId).orElseThrow(
                () -> new RuntimeException("Address not found"));
        User user = userRepository.findById(tokenUtil.getUserId()).orElseThrow(
                () -> new RuntimeException("User not found"));
        Order order = new Order(address, user, cart);
        orderRepository.save(order);
        userRepository.clearCart(tokenUtil.getUserId());

        messagingTemplate.convertAndSend("/topic/notifications/" + user.getId(),
                "Order created successfully");

    }

    /******************************************************************************************************* */
    public void checkout(Integer orderId) {
        if (!orderRepository.isUserIsOwner(tokenUtil.getUserId(), orderId))
            throw new RuntimeException("You are not authorized to cancel this order");
        User user = userRepository.findById(tokenUtil.getUserId()).orElseThrow(
                () -> new RuntimeException("User not found"));
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new RuntimeException("Order not found"));
        order.setStatus(DileveryStatus.ORDERED);

        Payment payment = new Payment("Credit Card", "USD", order, user);
        paymentRepository.save(payment);

        orderRepository.save(order);

    }

    /******************************************************************************************************* */
    public void cancelOrder(Integer orderId) {
        if (!orderRepository.isUserIsOwner(tokenUtil.getUserId(), orderId))
            throw new RuntimeException("You are not authorized to cancel this order");

        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new RuntimeException("Order not found"));
        order.setStatus(DileveryStatus.CANCELLED);
        orderRepository.save(order);
        messagingTemplate.convertAndSend("/topic/notifications/" + order.getUser().getId(),
                "Order cancelled successfully");
    }
}
