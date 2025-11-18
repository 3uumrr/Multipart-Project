package com.example.multipartTest.services.product;

import com.example.multipartTest.models.Product;
import com.example.multipartTest.request.ProductRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProductService {
    Product getById(Long id);
    List<Product> getAll();
    Product addProduct(ProductRequest product, MultipartFile[] images);

    List<Product> findByCriteria(Map<String, String> searchCriteria);
}
