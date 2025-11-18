package com.example.multipartTest.specification;

import com.example.multipartTest.models.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> containsName(String providedName){
        return (root,query,criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),"%" + providedName.toLowerCase() + "%");
    }

    public static Specification<Product> hasMinPrice(double providedPrice){
        return (root,query,criteriaBuilder)
                -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"),providedPrice);
    }

    public static Specification<Product> hasMaxPrice(double providedPrice){
        return (root,query,criteriaBuilder)
                -> criteriaBuilder.lessThanOrEqualTo(root.get("price"),providedPrice);
    }

}
