package com.example.tubespboo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.tubespboo.interfacee.Rateable;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "materials") 
public class Material implements Rateable {
    @Id
    private String id;

    private int stock;
    private int ratingCount;
    private String name;
    private double totrating;
    private double price;

    public Material() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getRatingCount() {
        if (ratingCount==0){
            return 0;
        }else{
            return ratingCount;
        }
        
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotrating() {
        if (totrating==0){
            return 0.0;
        }else{
           return totrating;
        }
        
    }

    public void setTotrating(double totrating) {
        this.totrating = totrating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @JsonIgnore
    @Override
    public double getAverage() {
        if (ratingCount == 0) {
            return 0.0;
        }
        return totrating / ratingCount;
    }

    @Override
    public void addRating(double rating) {
        if (rating >= 1 && rating <= 5) {
            this.totrating += rating;
            this.ratingCount++;
        } else {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
    }
}
