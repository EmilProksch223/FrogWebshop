package com.fh.webshopdemo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    public List<Product> findByManaType(String manaSymbolString) {
        return productRepository.findByManaType(manaSymbolString);
    }

    
    @PostMapping
public Product createProduct(@RequestBody Product product){
    return productRepository.save(product);
}

}
