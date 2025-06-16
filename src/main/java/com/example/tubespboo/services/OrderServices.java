package com.example.tubespboo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.tubespboo.exception.ResourceNotFound;
import com.example.tubespboo.model.Customer;
import com.example.tubespboo.model.Material;
import com.example.tubespboo.model.Order;
import com.example.tubespboo.model.OrderDetails;
import com.example.tubespboo.repos.CustomerRepository;
import com.example.tubespboo.repos.MaterialRepository;
import com.example.tubespboo.repos.OrderDetailsRepository;
import com.example.tubespboo.repos.OrderRepository;
import com.example.tubespboo.utils.Util;

@Service
public class OrderServices {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MaterialRepository materialRepository;

    public void addOrder(Order order) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Material> materials = order.getMaterials();

        if (!(principal instanceof Customer)) {
            throw new RuntimeException("Current user is not a customer");
        }

        Customer customer = (Customer) principal;

        Map<String, Long> materialCounts = materials.stream()
                .collect(Collectors.groupingBy(Material::getName, Collectors.counting()));

        List<Material> resolvedMaterials = materialCounts.entrySet().stream().map(entry -> {
            String materialName = entry.getKey();
            int quantityRequested = entry.getValue().intValue();

            Material found = materialRepository.findByName(materialName);
            if (found == null) {
                throw new RuntimeException("Material not found: " + materialName);
            }

            if (found.getStock() >= quantityRequested) {
                found.setStock(found.getStock() - quantityRequested);
                return found;
            } else {
                throw new ResourceNotFound(found.getName() + " stock is not enough");
            }
        }).collect(Collectors.toList());

        materialRepository.saveAll(resolvedMaterials);
        order.setMaterials(resolvedMaterials);

        order.calculateTotal();
        order.setId(Util.generateRandomId());
        order.setName(Util.generateRandomString(7));
        orderRepository.save(order);

        if (customer.getOrders() == null) {
            customer.setOrders(new ArrayList<>());
        }
        customer.getOrders().add(order);
        customerRepository.save(customer);

        OrderDetails details = new OrderDetails();
        details.setOrder(order);
        details.setId(order.getId());
        details.setPayDate(new Date());
        details.setDeliveryAddress(customer.getAddress());
        details.setTrackingNumber(Util.generateRandomTrackingNumber());
        details.setStatus("Pending");
        details.setDeliveryStatus("Not delivered");
        details.setPayId(Util.generateRandomId());
        details.setDeliverId(Util.generateRandomId());
        details.setPayMethod("Not yet paid");

        details.setCustomer(customer);
        orderDetailsRepository.save(details);
    }

    public List<Order> getMyOrders() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof Customer)) {
            throw new RuntimeException("Current user is not a customer");
        }
        Customer customer = (Customer) principal;

        return customer.getOrders();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<OrderDetails> getAllOrdersDetail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof Customer)) {
            throw new RuntimeException("Current user is not a customer");
        }
        Customer customer = (Customer) principal;
        return orderDetailsRepository.findByCustomer(customer);
    }
}
