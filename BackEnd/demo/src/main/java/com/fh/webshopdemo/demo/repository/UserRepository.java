package com.fh.webshopdemo.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fh.webshopdemo.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

    List<User> findByType(String type);



}
