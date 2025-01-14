package com.example.e_commerce.dto;

import com.example.e_commerce.model.DileveryStatus;

import lombok.Data;

@Data
public class UpdateOrderStatusRequest {

    private Integer orderId;
    private DileveryStatus status;
}
