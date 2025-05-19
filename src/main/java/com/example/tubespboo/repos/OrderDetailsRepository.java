package com.example.tubespboo.repos;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.tubespboo.model.OrderDetails;

public interface OrderDetailsRepository extends MongoRepository<OrderDetails, String>{
    OrderDetails findByOrder_Id(int id);
}
