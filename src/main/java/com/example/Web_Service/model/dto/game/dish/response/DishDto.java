package com.example.Web_Service.model.dto.game.dish.response;

import com.example.Web_Service.model.enums.Category;

public class DishDto {
    private int dishId;
    private String dishName;
    private Category category;
    private int dishPrice;

    public DishDto (int dishId, String dishName, Category category, int dishPrice) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.category = category;
        this.dishPrice = dishPrice;
    }

    public DishDto () {}

    public int getDishId() {
        return dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public Category getCategory() {
        return category;
    }

    public int getDishPrice() {
        return dishPrice;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDishPrice(int dishPrice) {
        this.dishPrice = dishPrice;
    }
}