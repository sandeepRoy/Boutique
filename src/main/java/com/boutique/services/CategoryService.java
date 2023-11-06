package com.boutique.services;

import com.boutique.dtos.CategoryDto;
import com.boutique.models.Category;
import com.boutique.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());
        category.setDescription(categoryDto.getDescription());
        category.setImageUrl(categoryDto.getImageUrl());
        category.setProducts(categoryDto.getProducts());
        return category;
    }
    public List<Category> listCategory(){
        return categoryRepository.findAll();
    }

    public Category readCategory(String categoryName){
        return categoryRepository.findByCategoryName(categoryName);
    }

    public Optional<Category> readCategory(Integer categoryId){
        return categoryRepository.findById(categoryId);
    }

    public Category updateCategory(Integer categoryId, CategoryDto categoryDto){
        Category category = categoryRepository.findById(categoryId).get();
        category.setCategoryName(categoryDto.getCategoryName());
        category.setDescription(categoryDto.getDescription());
        category.setImageUrl(categoryDto.getImageUrl());
        category.setProducts(categoryDto.getProducts());
        return category;
    }

    public void deleteCategory(Integer categoryId){
        Category category = categoryRepository.findById(categoryId).get();
        categoryRepository.delete(category);
    }
}
