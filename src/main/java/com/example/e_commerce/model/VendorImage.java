package com.example.e_commerce.model;

import com.example.e_commerce.dto.MediaDto;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class VendorImage {

    @Id
    private String Id;
    private String url;
    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Vendor vendor;

    public VendorImage(MediaDto imageDto) {
        this.Id = imageDto.getId();
        this.url = imageDto.getUrl();
    }
}
