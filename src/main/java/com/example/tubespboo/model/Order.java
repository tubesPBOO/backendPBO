package com.example.tubespboo.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private String name;
    private double price;
    private List<Material> materials;
    private List<Tukang> tukangs;

<<<<<<< HEAD
    public Order(){
        this.materials = new ArrayList<>();
        this.tukangs = new ArrayList<>();
    }

    public double calculateTotal() {
        return materials;

        if (condition:var(boolean)) {
            
        }
=======
    // public order(){
        // this.materials = new arrayList<>();
        // this.tukangs = new arrayList<>();
    // }

    public double calculateTotal() {
        return 0.0;
>>>>>>> 9af4e9e6ac7b6f5ba2319287f4dd445678df6c17
    }

    public void addMaterial() {

    }

    public void addTukang() {

    }
}
