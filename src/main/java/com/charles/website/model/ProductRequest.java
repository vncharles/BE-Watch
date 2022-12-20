package com.charles.website.model;


import lombok.Data;

@Data
public class ProductRequest {
    private String title;
    private String description;
    private Integer stock;
    private Double price;
    private Long brandId;
    private Long categoryId;
}
