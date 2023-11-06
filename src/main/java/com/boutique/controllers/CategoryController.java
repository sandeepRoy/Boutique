package com.boutique.controllers;

import com.boutique.dtos.CategoryDto;
import com.boutique.models.Category;
import com.boutique.repositories.CategoryRepository;
import com.boutique.services.CategoryService;
import com.boutique.commons.APIResponse;
import com.boutique.utilities.Helper;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/admin")

public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/get_category_all")
    public ResponseEntity<List<Category>> getCategory(){
        List<Category> list_category = categoryService.listCategory();
        return new ResponseEntity<List<Category>>(list_category, HttpStatus.OK);
    }

    @GetMapping("/filter_category")
    public ResponseEntity<List<Category>> filterCategory(
            @RequestParam(required = false) String name
    ){
        Category category = new Category();
        category.setCategoryName(name);
        Example<Category> categoryExample = Example.of(category);
        List<Category> category_filtered_byName = categoryRepository.findAll(categoryExample);
        return new ResponseEntity<List<Category>>(category_filtered_byName  , HttpStatus.OK);
    }

    @PostMapping("/create_category")
    public ResponseEntity<APIResponse> createCategory(@RequestBody CategoryDto categoryDto){
        Category category = categoryService.createCategory(categoryDto);
        categoryRepository.save(category);
        return new ResponseEntity<APIResponse>(new APIResponse(true, "Category Created: " + category.getCategoryName()), HttpStatus.CREATED);
    }

    @PutMapping("/update_category/{categoryId}")
    public ResponseEntity<APIResponse> updateCategory(@RequestParam Integer categoryId, @RequestBody CategoryDto categoryDto){
        if(Helper.notNull(categoryService.readCategory(categoryId))){
            Category category = categoryService.updateCategory(categoryId, categoryDto);
            categoryRepository.save(category);
            return new ResponseEntity<APIResponse>(new APIResponse(true, "Category Updated"), HttpStatus.OK);
        }
        return new ResponseEntity<APIResponse>(new APIResponse(false, "Category Can't be Updated!"), HttpStatus.OK);
    }

    @DeleteMapping("/delete_category/{categoryId}")
    public ResponseEntity<APIResponse> deleteCategory(@PathVariable("categoryId") Integer categoryId){
        // check if category exists already
        if(Helper.notNull(categoryService.readCategory(categoryId))){
            // if category found with given id, then delete
            categoryService.deleteCategory(categoryId);
            return new ResponseEntity<APIResponse>(new APIResponse(true, "Category Deleted"), HttpStatus.OK);
        }
        return new ResponseEntity<APIResponse>(new APIResponse(false, "Category Doesn't exists!"), HttpStatus.NOT_FOUND);
    }
}
