package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.DishDto;
import com.example.Web_Service.model.entity.Dish;
import com.example.Web_Service.repository.DishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {
    private final DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<DishDto> getListDishDto () {
        List<Dish> list = dishRepository.findAll();

        if (list.isEmpty()) {
            return List.of();
        }

        List<DishDto> getListDishDto = list.stream()
                .map(dish -> new DishDto(
                        dish.getName(),
                        dish.getCategory(),
                        dish.getPrice()
                ))
                .toList();

        return getListDishDto;
    }
}