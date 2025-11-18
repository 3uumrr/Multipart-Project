package com.example.multipartTest.services.image;

import com.example.multipartTest.models.Image;
import com.example.multipartTest.models.Product;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService  {
    Image saveImage(MultipartFile file , Product product);
    void deleteImage(Long id);
    Image updateImage(Long id, MultipartFile newImage);
    Image getImageById(Long id);

}
