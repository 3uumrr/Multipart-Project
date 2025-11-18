package com.example.multipartTest.mapper;

import com.example.multipartTest.models.Product;
import com.example.multipartTest.response.ImageResponse;
import com.example.multipartTest.response.ProductResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {

    public ProductResponse toProductResponse(Product product) {
        ProductResponse dto = new ProductResponse();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());

        List<ImageResponse> imgs = product.getImages()
                .stream()
                .map(img -> {
                    ImageResponse res = new ImageResponse();
                    res.setId(img.getId());
                    res.setUrl(img.getFilePath());
                    return res;
                }).toList();

        dto.setImages(imgs);

        return dto;
    }

    public List<ProductResponse> toProductResponse(List<Product> products) {

        return products.stream()
                .map(this::toProductResponse)
                .toList();
    }
}
