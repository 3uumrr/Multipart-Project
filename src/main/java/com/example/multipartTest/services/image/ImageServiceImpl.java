package com.example.multipartTest.services.image;

import com.example.multipartTest.exceptions.NotFoundException;
import com.example.multipartTest.models.Image;
import com.example.multipartTest.models.Product;
import com.example.multipartTest.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    private static final String UPLOAD_DIR = "uploads/";

    @Value("${app.base-url}")
    private String baseUrl;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Image saveImage(MultipartFile file, Product product) {

        String savedFileName = generateUniqueFileName(file.getOriginalFilename());
        Path filePath = Paths.get(UPLOAD_DIR, savedFileName);

        try {
            createUploadDirectoryIfNotExists(filePath);
            saveFileToDisk(file, filePath);

            Image image = buildImageEntity(file, savedFileName, product);
            return imageRepository.save(image);

        } catch (IOException ex) {
            throw new RuntimeException("Failed to save image file", ex);
        }
    }

    @Override
    public void deleteImage(Long id) {
        Image image = getImageById(id);

        // Delete from file system
        try {
            Files.deleteIfExists(Paths.get(UPLOAD_DIR,image.getFileName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Delete from DB
        imageRepository.delete(image);
    }


    @Override
    public Image updateImage(Long id, MultipartFile newImage) {
        Image old  = getImageById(id);
        String fileName = generateUniqueFileName(newImage.getOriginalFilename());
        try {
            Files.write(Paths.get(UPLOAD_DIR+fileName),newImage.getBytes());
            Files.deleteIfExists(Paths.get(UPLOAD_DIR+old.getFileName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        old.setFileType(newImage.getContentType());
        old.setFilePath(baseUrl+ "/" +UPLOAD_DIR + fileName);
        old.setFileName(fileName);
        return  imageRepository.save(old);
    }

    @Override
    public Image getImageById(Long id){
        return imageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Image Not Found"));
    }


    private String generateUniqueFileName(String originalName) {
        return System.currentTimeMillis() + "_" + originalName;
    }

    private void createUploadDirectoryIfNotExists(Path filePath) throws IOException {
        Files.createDirectories(filePath.getParent());
    }

    private void saveFileToDisk(MultipartFile file, Path path) throws IOException {
        Files.write(path, file.getBytes());
    }

    private Image buildImageEntity(MultipartFile file, String savedFileName, Product product) {

        Image image = new Image();

        image.setFileName(savedFileName); // <-- حفظ الاسم الصحيح للملف
        image.setFileType(file.getContentType());
        image.setFilePath(baseUrl + "/" + UPLOAD_DIR + savedFileName);
        image.setProduct(product);

        return image;
    }
}

