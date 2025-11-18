package com.example.multipartTest.controller;


import com.example.multipartTest.mapper.ProductMapper;
import com.example.multipartTest.models.Product;
import com.example.multipartTest.request.ProductRequest;
import com.example.multipartTest.response.ApiResponse;
import com.example.multipartTest.response.ProductResponse;
import com.example.multipartTest.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper mapper;


    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<Product> product = productService.getAll();

        List<ProductResponse> response = mapper.toProductResponse(product);

        return ResponseEntity.ok(new ApiResponse("Success",response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProduct(@PathVariable Long id){
        Product product = productService.getById(id);
        ProductResponse response = mapper.toProductResponse(product);
        return ResponseEntity.ok(new ApiResponse("Success",response));
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> addProduct(
            @RequestPart(name = "product") ProductRequest productRequest,
            @RequestPart(required = false , name = "images") MultipartFile[] images) {

        Product product = productService.addProduct(productRequest,images);

        ProductResponse response = mapper.toProductResponse(product);

        return ResponseEntity.ok(new ApiResponse("Product Added Successfully",response));
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse> findProductByCriteria(@RequestBody Map<String,String> searchCriteria) {
        List<Product> products = productService.findByCriteria(searchCriteria);
        List<ProductResponse> response = mapper.toProductResponse(products);
        return ResponseEntity.ok(new ApiResponse("Success",response));

    }



}
