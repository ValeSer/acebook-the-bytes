package com.makersacademy.acebook.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.makersacademy.acebook.config.CloudinaryConfiguration;
import org.imgscalr.Scalr;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Map;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryService(CloudinaryConfiguration cloudinaryConfiguration) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudinaryConfiguration.getCloudName(),
                "api_key", cloudinaryConfiguration.getApiKey(),
                "api_secret", cloudinaryConfiguration.getApiSecret()
        ));
    }

    public String uploadImage(MultipartFile photo) {
        try {
            BufferedImage originalImage = ImageIO.read(photo.getInputStream());
            BufferedImage resizedImage = Scalr.resize(originalImage, Scalr.Method.QUALITY, 800, 800);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "jpg", baos);
            baos.flush();
            byte[] compressedImageBytes = baos.toByteArray();
            baos.close();

            Map<String, Object> uploadResult = cloudinary.uploader().upload(compressedImageBytes, ObjectUtils.emptyMap());
            return (String) uploadResult.get("url");
        } catch (Exception e) {
            System.out.println("Error uploading image: " + e.getMessage());
            return null;
        }
    }

}
