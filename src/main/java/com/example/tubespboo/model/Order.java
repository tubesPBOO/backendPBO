package com.example.tubespboo.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "orders")
public class Order {
    @Id
    private int id;

    private String name;
    private double price;
    private List<Material> materials = new ArrayList<>();
    private List<Tukang> tukangs = new ArrayList<>();
    private Customer customer;

    public Order() {}

    public double calculateTotal() {
        double total = 0.0;
        for (Material material : materials) {
            total += material.getPrice();
        }
        this.price = total; 
        return total;
    }

    public void addMaterial(Material material) {
        this.materials.add(material);
        calculateTotal();
    }

    public void addTukang(Tukang tukang) {
        this.tukangs.add(tukang);
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public List<Tukang> getTukangs() {
        return tukangs;
    }

    public void setTukangs(List<Tukang> tukangs) {
        this.tukangs = tukangs;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
