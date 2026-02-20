package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.adminDto.ProductDto;
import com.example.Web_Service.model.entity.Product;
import com.example.Web_Service.model.enums.CategoryProduct;
import com.example.Web_Service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> getListProductDto () {
        List<Product> list = productRepository.findAll();

        if (list.isEmpty()) {
            return List.of();
        }

        List<ProductDto> getListProductDto = list.stream()
                .map(product -> new ProductDto(
                        product.getName(),
                        product.getCategory(),
                        product.getPrice()
                ))
                .toList();

        return getListProductDto;
    }

    public String createProductValidator (String name) {
        if (productRepository.findByName(name.trim()).orElse(null) != null) {
            return "Такой товар уже есть в базе данных!";
        }

        if (name.trim().isBlank()) {
            return "Название товара не может быть пустым!";
        }

        return null;
    }

    public String updateValidator (String name) {
        if (productRepository.findByName(name.trim()).orElse(null) != null) {
            return "Такой товар уже есть в базе данных!";
        }

        return null;
    }

    public String createNewProduct (Product product) {
        productRepository.save(product);

        return null;
    }

    public Product getProduct (int id) {
        return productRepository.findById(id).orElse(null);
    }

    public String updateDataProduct (Product product) {
        productRepository.save(product);
        return null;
    }
}