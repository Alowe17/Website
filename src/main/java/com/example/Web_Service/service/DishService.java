package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.adminDto.DishDto;
import com.example.Web_Service.model.entity.Dish;
import com.example.Web_Service.model.enums.Category;
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

    public String validator (String name) {
        if (dishRepository.findByName(name.trim()).orElse(null) != null) {
            return "Это блюдо уже есть в базе данных!";
        }

        return null;
    }

    public String createNewDish (Dish dish) {
        dishRepository.save(dish);

        return null;
    }

    public Dish getDish (int id) {
        Dish dish = dishRepository.findById(id).orElse(null);

        return dish;
    }

    public String updateDataDish (Dish dish) {
        dishRepository.save(dish);
        return null;
    }
}