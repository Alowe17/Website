package com.example.Web_Service.repository;

import com.example.Web_Service.model.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {
    @Query("SELECT u FROM Dish u")
    List<Dish> findAllDish();
    Optional<Dish> findByName(String name);
    Optional<Dish> findById(Integer id);
}