package com.example.Web_Service.model.dto.adminDto.dish.request;

import com.example.Web_Service.model.enums.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class DishCreateDto {
    @NotBlank(message = "Название блюда не может быть пустым!")
    private String name;
    @Positive(message = "Цена не может быть отрицательной или равной нулю!")
    private Integer price;
    private Category category;

    public DishCreateDto(String name, Integer price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public DishCreateDto() {}

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}