package com.example.e_commerce.model;

import com.example.e_commerce.dto.ImageDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class VendorImage {

    @Id
    private String Id;
    private String url;
    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Vendor vendor;

    public VendorImage(ImageDto imageDto) {
        this.Id = imageDto.getId();
        this.url = imageDto.getUrl();
    }
}
