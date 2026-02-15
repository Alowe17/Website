package com.example.Web_Service.model.dto;

import com.example.Web_Service.model.enums.CategoryProduct;

public class ProductDto {
    private String name;
    private CategoryProduct category;
    private int price;

    public ProductDto(String name, CategoryProduct category, int price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public ProductDto() {}

    public String getName() {
        return name;
    }

    public CategoryProduct getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(CategoryProduct category) {
        this.category = category;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}