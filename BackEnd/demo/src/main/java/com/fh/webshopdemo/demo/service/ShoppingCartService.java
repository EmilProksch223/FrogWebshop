package com.fh.webshopdemo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.webshopdemo.demo.model.ShoppingCart;
import com.fh.webshopdemo.demo.repository.ShoppingCartRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public List<ShoppingCart> getAll() {
        return shoppingCartRepository.findAll();
    }

    public ShoppingCart getById(Long id) {
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findById(id);
        if (shoppingCart.isPresent()){
            return shoppingCart.get();
        }else {
            throw new EntityNotFoundException("ShoppingCart not found with id: " + id);
        }
    }

    public ShoppingCart save(ShoppingCart shoppingCart){
        return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart update(Long id, ShoppingCart shoppingCart) {
        ShoppingCart existingCart = getById(id);
        existingCart.setUser(shoppingCart.getUser());
        existingCart.setProducts(shoppingCart.getProducts());
        return shoppingCartRepository.save(existingCart);
    }

    public void deleteById(Long id){
        shoppingCartRepository.deleteById(id);
    }
    

    
}
