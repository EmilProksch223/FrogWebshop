package com.fh.webshopdemo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fh.webshopdemo.demo.service.ShoppingCartService;
import com.fh.webshopdemo.demo.model.ShoppingCart;


@RestController
@RequestMapping("/api/shoppingcarts")
public class ShoppingCartController {
    
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/{cartId}/products/{productId}")
    public ShoppingCart addProductToCart(@PathVariable("cartId") Long cartId, @PathVariable("productId") Long productId) {
        return shoppingCartService.addProductToCart(cartId, productId);
    }
}