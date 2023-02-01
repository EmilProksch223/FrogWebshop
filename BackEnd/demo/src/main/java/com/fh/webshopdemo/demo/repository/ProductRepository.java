package com.fh.webshopdemo.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fh.webshopdemo.demo.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
