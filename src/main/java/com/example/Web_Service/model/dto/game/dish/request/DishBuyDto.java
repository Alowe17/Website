package com.example.Web_Service.model.dto.game.dish.request;

import java.util.List;

public class DishBuyDto {
    private List<Integer> dishIds;

    public DishBuyDto (List<Integer> dishIds) {
        this.dishIds = dishIds;
    }

    public List<Integer> getDishIds() {
        return dishIds;
    }

    public void setDishIds(List<Integer> dishIds) {
        this.dishIds = dishIds;
    }
}