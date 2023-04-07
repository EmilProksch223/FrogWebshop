package com.fh.webshopdemo.demo.controller;

import java.util.ArrayList;
import java.util.List;

import com.fh.webshopdemo.demo.model.Product;
import com.fh.webshopdemo.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts(
            @RequestParam(name = "manasymbols", required = false) String manaSymbolsString, 
            @RequestParam(name = "searchterm", required = false) String searchterm) {
        
        List<Product> allProducts = productService.getAllProducts();
        List<Product> matchingProducts = new ArrayList<>();
    
        if (manaSymbolsString == null && searchterm == null) {
            return allProducts;
        }
        for (Product product : allProducts) {
            if (manaSymbolsString != null && !product.getManaType().toLowerCase().contains(manaSymbolsString.toLowerCase())) {
                continue; // Skip this product
            }
            if (searchterm != null && !product.getName().toLowerCase().contains(searchterm.toLowerCase())) {
                continue; // Skip this product
            }
            matchingProducts.add(product);
        }
        return matchingProducts;
    }

    // Produkt erstellen

    @PostMapping
    public Product createProduct(@Valid @RequestBody Product product) {
        return productService.createProduct(product);
    }
}
