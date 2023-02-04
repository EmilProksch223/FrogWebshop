package com.fh.webshopdemo.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fh.webshopdemo.demo.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{


    List<Product> findByManaType(String string);



}
