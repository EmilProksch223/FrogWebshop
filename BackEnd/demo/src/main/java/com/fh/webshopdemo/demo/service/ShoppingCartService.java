package com.fh.webshopdemo.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.webshopdemo.demo.model.Product;

import com.fh.webshopdemo.demo.model.ShoppingCart;

import com.fh.webshopdemo.demo.repository.ShoppingCartRepository;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    public List<Product> getProductsInCart(Long cartId) {
        // get the ShoppingCart object from the database
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new NoSuchElementException("ShoppingCart not found with ID: " + cartId));

        // get the List<Product> products attribute of the ShoppingCart object
        return shoppingCart.getProducts();
    }

    public ShoppingCart addProductToCart(Long cartId, Long productId) {
        // get the ShoppingCart object from the database
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new NoSuchElementException("ShoppingCart not found with ID: " + cartId));

        // get the Product object from the database
        Optional<Product> productOptional = productService.getProductById(productId);
        Product product = productOptional
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + productId));

        // add the Product object to the List<Product> products attribute of the
        // ShoppingCart object
        shoppingCart.getProducts().add(product);

        // save the updated ShoppingCart object to the database
        return shoppingCartRepository.save(shoppingCart);
    }
}
