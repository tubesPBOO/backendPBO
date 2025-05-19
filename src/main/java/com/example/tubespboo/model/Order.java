package com.example.tubespboo.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class Order {

    private int id;
    private String name;
    private double price;
    private List<Material> materials = new ArrayList<>();
    private List<Tukang> tukangs = new ArrayList<>();

    private Customer customer; 

    public Order() {}

    public double calculateTotal() {
        return 0.0;
    }

    // public order(){
        // this.materials = new arrayList<>();
        // this.tukangs = new arrayList<>();
    // }


    public void addMaterial(Material material) {
        this.materials.add(material);
    }

    public void addTukang(Tukang tukang) {
        this.tukangs.add(tukang);
    }

    // Getters and setters
}
