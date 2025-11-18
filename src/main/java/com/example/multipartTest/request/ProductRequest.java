package com.example.multipartTest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductRequest {
    private String name;
    private double price;
    private int quantity;
}
