package com.example.tubespboo.repos;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.tubespboo.model.Order;

public interface OrderRepository extends MongoRepository<Order, String>{
    Order findByName(String name);
}