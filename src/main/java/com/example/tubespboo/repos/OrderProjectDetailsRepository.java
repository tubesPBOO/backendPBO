package com.example.tubespboo.repos;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.tubespboo.model.OrderProjectDetails;

public interface  OrderProjectDetailsRepository extends MongoRepository<OrderProjectDetails,String> {
    
}
