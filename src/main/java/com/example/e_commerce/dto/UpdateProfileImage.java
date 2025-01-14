package com.example.e_commerce.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UpdateProfileImage {
    private MultipartFile image;

}
