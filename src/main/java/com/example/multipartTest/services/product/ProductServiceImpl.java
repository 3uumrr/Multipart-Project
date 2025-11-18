package com.example.multipartTest.services.product;

import com.example.multipartTest.exceptions.NotFoundException;
import com.example.multipartTest.models.Image;
import com.example.multipartTest.models.Product;
import com.example.multipartTest.repository.ProductRepository;
import com.example.multipartTest.request.ProductRequest;
import com.example.multipartTest.services.image.ImageService;
import com.example.multipartTest.specification.ProductSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j // for logs
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageService imageService;

    @Override
    public Product getById(Long id) {
        log.info("Fetching product by id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() ->{
                    log.warn("Product with id {} not found", id);
                    return new NotFoundException("Product Not Found!");
                });
        log.info("Found product: {} with id: {}", product.getName(), product.getId());
        return product;
    }

    @Override
    public List<Product> getAll() {
        log.info("Fetching all products");
        List<Product> products = productRepository.findAll();
        log.info("Found {} products", products.size());
        return products;
    }

    @Override
    public Product addProduct(ProductRequest productRequest, MultipartFile[] images) {
        log.info("Adding new product: {}", productRequest.getName());

        Product product = new Product();
        product.setName(productRequest.getName());
        product.setQuantity(productRequest.getQuantity());
        product.setPrice(productRequest.getPrice());

        // 1️⃣ حفظ المنتج
       product = productRepository.save(product);
        log.info("Saved product with id: {}", product.getId());

        // 2️⃣ حفظ الصور وربطها بالمنتج
        List<Image> savedImages = new ArrayList<>();
       for (MultipartFile file : images){
           Image image = imageService.saveImage(file, product);
           log.info("Saved image {} for product id: {}", image.getFileName(), product.getId());
           savedImages.add(image);
       }

       product.setImages(savedImages);

       return product;

    }

    @Override
    public List<Product> findByCriteria(Map<String, String> searchCriteria) {
        log.info("Searching products by criteria: {}", searchCriteria);

        Specification<Product> spec = Specification.where(null);

        if (searchCriteria.get("name") != null){
            log.info("Filtering by name containing: {}", searchCriteria.get("name"));
            spec = spec.and(ProductSpecification.containsName(searchCriteria.get("name")));
        }

        if (searchCriteria.get("minPrice") != null){
            double minPrice = Double.parseDouble(searchCriteria.get("minPrice"));
            log.info("Filtering by min price: {}", minPrice);
            spec = spec.and(ProductSpecification.hasMinPrice(minPrice));        }

        if (searchCriteria.get("maxPrice") != null){
            double maxPrice = Double.parseDouble(searchCriteria.get("maxPrice"));
            log.info("Filtering by max price: {}", maxPrice);
            spec = spec.and(ProductSpecification.hasMaxPrice(maxPrice));        }

        List<Product> results = productRepository.findAllWithImages(spec);
        log.info("Found {} products matching criteria", results.size());

        return results;

    }
}
