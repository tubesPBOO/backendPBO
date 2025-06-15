package com.example.tubespboo.repos;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.tubespboo.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    Customer findByNameAndPassword(String name, String password);

    boolean existsByEmail(String email);

    boolean existsByName(String name);

    boolean existsByPhoneNumber(String number);

    Customer findByName(String name);

    Customer findByEmail(String username);

    void deleteByName(String name);
}
