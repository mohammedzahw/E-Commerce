package com.example.e_commerce.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.e_commerce.dto.MediaDto;
import com.example.e_commerce.exception.CustomException;

@Service
public class MediaService {

    private CloudinaryService cloudinaryService;

    public MediaService(CloudinaryService cloudinaryService) {

        this.cloudinaryService = cloudinaryService;
    }

    /************************************************************************* */

    @SuppressWarnings("rawtypes")
    public MediaDto uploadImage(MultipartFile image, String imageId, String folder) throws IOException {
        if (image == null) {
            throw new CustomException("Image is required", HttpStatus.BAD_REQUEST);
        }
        try {
            BufferedImage bi = ImageIO.read(image.getInputStream());
            if (bi == null) {
                throw new CustomException("Invalid image file", HttpStatus.BAD_REQUEST);
            }
            Map result = cloudinaryService.upload(image, folder);
            if (imageId != null)
                cloudinaryService.delete(imageId);

            MediaDto imageDto = new MediaDto();
            imageDto.setId(result.get("public_id").toString());
            imageDto.setUrl(result.get("url").toString());

            return imageDto;

        } catch (CustomException e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return null;
        }
    }

    /***************************************************************************************************** */

    @SuppressWarnings("rawtypes")
    public MediaDto uploadVideo(MultipartFile video, String videoId, String folder) throws IOException {
        if (video == null) {
            throw new CustomException("Video is required", HttpStatus.BAD_REQUEST);
        }
        String contentType = video.getContentType();
        System.out.println(contentType);
        if (contentType == null || !contentType.startsWith("video/")) {
            throw new CustomException("Invalid video file", HttpStatus.BAD_REQUEST);
        }
        try {
            Map result = cloudinaryService.upload(video, folder);
            if (videoId != null)
                cloudinaryService.delete(videoId);

            MediaDto videoDto = new MediaDto();
            videoDto.setId(result.get("public_id").toString());
            videoDto.setUrl(result.get("url").toString());
            return videoDto;
        } catch (IOException e) {
            throw new CustomException("Error uploading video", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*********************************************************************************************/

    public void delete(String id) throws IOException {
        cloudinaryService.delete(id);

    }

    /**
     * @throws Exception
     ********************************************************************************************/

    public void deleteByFolder(String folder) throws Exception {
        cloudinaryService.deleteByFolder(folder);

    }

    /**********************************************************************************************/
}
