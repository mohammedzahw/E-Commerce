package com.example.e_commerce.service;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.e_commerce.config.TokenUtil;
import com.example.e_commerce.dto.MediaDto;
import com.example.e_commerce.dto.UpdateProfileImage;
import com.example.e_commerce.exception.CustomException;
import com.example.e_commerce.model.Vendor;
import com.example.e_commerce.model.VendorImage;
import com.example.e_commerce.repository.VendorRepository;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;
    private final MediaService mediaService;
    private final TokenUtil tokenUtil;

    public VendorService(VendorRepository vendorRepository, MediaService mediaService, TokenUtil tokenUtil) {
        this.vendorRepository = vendorRepository;
        this.mediaService = mediaService;
        this.tokenUtil = tokenUtil;
    }

    /******************************************************************************************** */
    @Transactional
    public void updateProfile(UpdateProfileImage updateProfileImage) throws IOException {
        Vendor vendor = vendorRepository.findById(tokenUtil.getUserId())
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        if (updateProfileImage.getImage() != null) {
            MediaDto imageDto = mediaService.uploadImage(updateProfileImage.getImage(), vendor.getVendorImage().getId(),
                    "vendor" + vendor.getId());
            VendorImage vendorImage = new VendorImage(imageDto);
            vendor.setVendorImage(vendorImage);
            vendorRepository.save(vendor);
        }

    }
}
