package com.example.tubespboo.repos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.tubespboo.model.Order;

public interface OrderRepository extends MongoRepository<Order, String>{
    List<Order> findByTukangs_Id(String tukangId);
    Order findByName(String name);
}