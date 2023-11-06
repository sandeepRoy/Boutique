package com.boutique.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boutique.dtos.ProductDto;
import com.boutique.models.Category;
import com.boutique.models.Product;
import com.boutique.repositories.ProductRepository;

import java.util.*;
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


    public void createProduct(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setCategory(category);

        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        List<Product> all = productRepository.findAll();
        return all;
    }


    public void updateproduct(ProductDto productDto, Optional<Product> optionalProduct) {
        Product product = optionalProduct.get();
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());

        productRepository.save(product);
    }

    public void deleteProduct(Optional<Product> optionalProduct) {
        Product product = optionalProduct.get();
        productRepository.delete(product);
    }
}
