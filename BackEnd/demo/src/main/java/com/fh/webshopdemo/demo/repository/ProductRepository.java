package com.fh.webshopdemo.demo.repository;

import java.util.List;

import com.fh.webshopdemo.demo.model.Product;

public interface ProductRepository {

    List<Product> findAll();

    List<Product> findAllByType(String type);
}
