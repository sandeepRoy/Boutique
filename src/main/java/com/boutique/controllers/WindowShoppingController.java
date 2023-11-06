package com.boutique.controllers;

import com.boutique.models.Category;
import com.boutique.models.Product;
import com.boutique.repositories.CategoryRepository;
import com.boutique.repositories.ProductRepository;
import com.boutique.services.CategoryService;
import com.boutique.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shop")

public class WindowShoppingController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

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

    @GetMapping("get_product_all")
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/filter_product")
    public ResponseEntity<List<Product>> filterProducts(
            @RequestParam(required = true) Integer categoryId,
            @RequestParam(required = false) Double price){
        Category category = new Category();
        category.setId(categoryId);

        Product product = new Product();
        product.setCategory(category);
        product.setPrice(price);

        Example<Product> productExample = Example.of(product);
        List<Product> all = productRepository.findAll(productExample);
        return new ResponseEntity<List<Product>>(all, HttpStatus.OK);
    }
}
