package com.example.tubespboo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.tubespboo.model.Customer;
import com.example.tubespboo.model.Material;
import com.example.tubespboo.model.Order;
import com.example.tubespboo.model.OrderDetails;
import com.example.tubespboo.model.Tukang;
import com.example.tubespboo.repos.CustomerRepository;
import com.example.tubespboo.repos.MaterialRepository;
import com.example.tubespboo.repos.OrderDetailsRepository;
import com.example.tubespboo.repos.OrderRepository;
import com.example.tubespboo.repos.TukangRepository;
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

    @Autowired
    private TukangRepository tukangRepository;

    public Order addOrder(Order order) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Material> materials = order.getMaterials();
        int tukangCount = order.getTukangCount();

        if (!(principal instanceof Customer)) {
            throw new RuntimeException("Current user is not a customer");
        }

        Customer customer = (Customer) principal;

        List<Material> resolvedMaterials = materials.stream().map(m -> {
            Material found = materialRepository.findByName(m.getName());
            if (found == null) {
                throw new RuntimeException("Material not found: " + m.getName());
            }
            return found;
        }).collect(Collectors.toList());
        order.setMaterials(resolvedMaterials);

        List<Tukang> availableTukangs = tukangRepository.findAll()
                .stream()
                .filter(Tukang::getAvailability)
                .collect(Collectors.toList());

        if (availableTukangs.size() < tukangCount) {
            throw new RuntimeException("Not enough available tukangs");
        }

        List<Tukang> assignedTukangs = availableTukangs.subList(0, tukangCount);

        for (Tukang t : assignedTukangs) {
            t.setAvailability(false);
            tukangRepository.save(t);
            order.addTukang(t);
        }

        order.calculateTotal();

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

        return order;
    }

    public List<Order> getMyOrders() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof Customer)) {
            throw new RuntimeException("Current user is not a customer");
        }
        Customer customer = (Customer) principal;

        return customer.getOrders();
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
