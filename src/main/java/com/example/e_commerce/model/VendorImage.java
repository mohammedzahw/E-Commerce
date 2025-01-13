package com.example.e_commerce.model;

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
    @OneToOne(mappedBy = "vendorImage", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private Vendor vendor;
}
