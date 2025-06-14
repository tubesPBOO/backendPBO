package com.example.tubespboo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tubespboo.model.Order;
import com.example.tubespboo.model.OrderDetails;
import com.example.tubespboo.services.OrderServices;

@RestController
@RequestMapping("/api/customers")
public class OrderController {

    @Autowired
    private OrderServices orderServices;

    @PostMapping("/order")
    public ResponseEntity<String> addOrder(@RequestBody Order order) {
        try {
            orderServices.addOrder(order);
            return ResponseEntity.ok("Order Added");
        }catch(RuntimeException err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getMessage());
        }
    }

    @GetMapping("/getOrdersDetail")
    public List<OrderDetails> getDetails() {
        return orderServices.getAllOrdersDetail();
    }
}
