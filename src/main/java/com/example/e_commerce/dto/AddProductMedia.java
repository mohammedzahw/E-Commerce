package com.example.e_commerce.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AddProductMedia {

    Integer productId;
    List<MultipartFile> files;

}
