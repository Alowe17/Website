package com.example.Web_Service.model.dto.adminDto;

import com.example.Web_Service.model.enums.CategoryProduct;
import jakarta.validation.constraints.PositiveOrZero;

public class ProductDto {
    private String name;
    private CategoryProduct category;
    @PositiveOrZero(message = "Цена не может быть отрицательной!")
    private Integer price;

    public ProductDto(String name, CategoryProduct category, Integer price) {
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

    public Integer getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(CategoryProduct category) {
        this.category = category;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}