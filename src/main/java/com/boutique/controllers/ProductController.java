package com.boutique.controllers;

import com.boutique.dtos.ProductDto;
import com.boutique.models.Category;
import com.boutique.models.Product;
import com.boutique.repositories.CategoryRepository;
import com.boutique.repositories.ProductRepository;
import com.boutique.services.ProductService;
import com.boutique.commons.APIResponse;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")

public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
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

    @PostMapping("/create_product")
    public ResponseEntity<APIResponse> createProduct(@RequestBody ProductDto productDto){
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if(!optionalCategory.isPresent()){
            return new ResponseEntity<APIResponse>(new APIResponse(false, "Category doesn't exists"), HttpStatus.BAD_REQUEST);
        }
        productService.createProduct(productDto, optionalCategory.get());
        return new ResponseEntity<APIResponse>(new APIResponse(true, "Product created"), HttpStatus.CREATED);
    }
    @PutMapping("/update_product/{productId}")
    public ResponseEntity<APIResponse> updateProduct(@PathVariable("productId") Integer productId, @RequestBody ProductDto productDto){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(!optionalProduct.isPresent()){
            return new ResponseEntity<APIResponse>(new APIResponse(false, "Product doesn't exists"), HttpStatus.BAD_REQUEST);
        }
        productService.updateproduct(productDto, optionalProduct);
        return new ResponseEntity<APIResponse>(new APIResponse(true, "Product Updated"), HttpStatus.OK);
    }
    @DeleteMapping("/delete_product/{productId}")
    public ResponseEntity<APIResponse> deleteProduct(@PathVariable("productId") Integer productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(!optionalProduct.isPresent()){
            return new ResponseEntity<APIResponse>(new APIResponse(false, "Product not found"), HttpStatus.OK);
        }
        productService.deleteProduct(optionalProduct);
        return new ResponseEntity<APIResponse>(new APIResponse(true, "Product Deleteed"), HttpStatus.OK);
    }
}
