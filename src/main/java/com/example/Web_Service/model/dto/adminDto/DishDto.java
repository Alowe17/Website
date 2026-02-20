package com.example.Web_Service.model.dto.adminDto;

import com.example.Web_Service.model.enums.Category;
import jakarta.validation.constraints.PositiveOrZero;

public class DishDto {
    private String name;
    private Category category;
    @PositiveOrZero(message = "Цена блюда не может быть отрицательной!")
    private Integer price;

    public DishDto(String name, Category category, Integer price) {
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

    public Integer getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}