package com.fh.webshopdemo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fh.webshopdemo.demo.model.Product;
import com.fh.webshopdemo.demo.repository.ProductRepository;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }




    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(long id) {
        return productRepository.findById(id);
    }


}
