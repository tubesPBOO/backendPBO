package com.example.tubespboo.repos;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderDetailsRepository extends MongoRepository<OrderDetails, String>{
    
}
