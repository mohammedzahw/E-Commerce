package com.example.e_commerce.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.e_commerce.dto.ImageDto;
import com.example.e_commerce.exception.CustomException;

@Service
public class ImageService {

    private CloudinaryService cloudinaryService;

    public ImageService(CloudinaryService cloudinaryService) {

        this.cloudinaryService = cloudinaryService;
    }

    /************************************************************************* */

    @SuppressWarnings("rawtypes")
    public ImageDto uploadImage(MultipartFile image, String imageId) throws IOException {
        if (image == null) {
            throw new CustomException("Image is required", HttpStatus.BAD_REQUEST);
        }
        try {
            BufferedImage bi = ImageIO.read(image.getInputStream());
            if (bi == null) {
                throw new CustomException("Invalid image file", HttpStatus.BAD_REQUEST);
            }
            Map result = cloudinaryService.upload(image, "user");
            if (imageId != null)
                cloudinaryService.delete(imageId);

            ImageDto imageDto = new ImageDto();
            imageDto.setId(result.get("public_id").toString());
            imageDto.setUrl(result.get("url").toString());

            return imageDto;

        } catch (CustomException e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return null;
        }
    }

}
