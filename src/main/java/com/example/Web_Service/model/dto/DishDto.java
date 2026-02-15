package com.example.Web_Service.model.dto;

import com.example.Web_Service.model.enums.Category;

public class DishDto {
    private String name;
    private Category category;
    private int price;

    public DishDto(String name, Category category, int price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public DishDto() {}

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}