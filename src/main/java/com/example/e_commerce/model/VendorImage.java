package com.example.e_commerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class VendorImage {

    @Id
    private String Id;
    private String url;
    @OneToOne(mappedBy = "vendorImage")
    private Vendor vendor;
}
