package com.example.Web_Service.service;

import com.example.Web_Service.model.dto.ProductDto;
import com.example.Web_Service.model.entity.Product;
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
}