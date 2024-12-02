/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.artem.sblearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.artem.sblearn.entity.Products;

/**
 *
 * @author abdul.artem
 */
public interface ProductsRepository extends JpaRepository<Products, Integer> {
    
}
