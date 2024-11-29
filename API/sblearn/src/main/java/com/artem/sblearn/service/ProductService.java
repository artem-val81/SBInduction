/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.haseeb.sblearn.service;

import com.haseeb.sblearn.entity.Products;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author abdul.haseeb
 */
public interface ProductService {
    
    List<Products> getAllProducts();
    
    Optional<Products> getProductById(Integer id);
    
}
