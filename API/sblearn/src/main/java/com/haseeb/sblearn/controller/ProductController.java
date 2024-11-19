/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haseeb.sblearn.controller;

import com.haseeb.sblearn.entity.Products;
import com.haseeb.sblearn.response.MessageResponse;
import com.haseeb.sblearn.service.ProductService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

/**
 *
 * @author abdul.haseeb
 */
@RestController
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @GetMapping
    public List<Products> getAllProducts() {
        return productService.getAllProducts();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Products> getProductById(@PathVariable Integer id) {
        Optional<Products> product = productService.getProductById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}
