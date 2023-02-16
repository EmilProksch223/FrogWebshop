package com.fh.webshopdemo.demo.controller;

import java.util.ArrayList;
import java.util.List;

import com.fh.webshopdemo.demo.model.Product;
import com.fh.webshopdemo.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts(@RequestParam(name = "search", required = false) String manaSymbolString) {

        List<Product> allProducts = productService.getAllProducts();
        List<Product> matchingProducts = new ArrayList<>();

        if (manaSymbolString == null) {
            return allProducts;
        }
        for (Product product : allProducts) {
            if (product.getManaType().contains(manaSymbolString)) {
                matchingProducts.add(product);
            }
        }
        return matchingProducts;
    }

    //Produkt erstellen

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

}
