/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haseeb.sblearn.service.impl;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import com.haseeb.sblearn.entity.Products;
import com.haseeb.sblearn.repository.ProductsRepository;
import com.haseeb.sblearn.service.ProductService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author abdul.haseeb
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final S3Client s3Client;

    @Value("${BUCKET_NAME}")
    private String S3bucket;

    @Value("${UPLOAD_PATH}")
    private String uploadPath;

    @Value("${IMPORT_PATH}")
    private String importPath;

    @Autowired
    public ProductServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Autowired
    private ProductsRepository productRepository;
    
    
    @Override
    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }
    
    @Override
    public Optional<Products> getProductById(Integer id) {
        return productRepository.findById(id);
    }

}
