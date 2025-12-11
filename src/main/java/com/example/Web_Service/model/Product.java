package com.example.Web_Service.model;

import com.example.Web_Service.model.enums.CategoryProduct;
import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryProduct category;
    @Column(name = "price", nullable = false)
    private int price;

    public Product (int id, String name, CategoryProduct category, int price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public Product() {}

    public void setId(int id) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CategoryProduct getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Информация о продукте: " + "\n" +
                "id: " + id + "\n" +
                "Название: " + name + "\n" +
                "Категория: " + category + "\n" +
                "Цена: " + price;
    }
}