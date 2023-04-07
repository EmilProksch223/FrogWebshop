package com.fh.webshopdemo.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fh.webshopdemo.demo.model.ShoppingCart;
import com.fh.webshopdemo.demo.service.ShoppingCartService;

@RestController
@RequestMapping("/api/shoppingcarts")
public class ShoppingCartController {
    
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public List<ShoppingCart>getAll(){
        return shoppingCartService.getAll();
    }

    @GetMapping("/{id}")
    public ShoppingCart getById(@PathVariable("id") Long id) {
        return shoppingCartService.getById(id);
    }

    @PostMapping
    public ShoppingCart create(@RequestBody ShoppingCart shoppingCart){
        return shoppingCartService.save(shoppingCart);
    }

    @PutMapping("/{id}")
    public ShoppingCart update(@PathVariable("id")Long id, @RequestBody ShoppingCart shoppingCart){
        return shoppingCartService.update(id, shoppingCart);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        shoppingCartService.deleteById(id);
    }
    
}
