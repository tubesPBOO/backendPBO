package com.example.tubespboo.model;

public class Material {
    private int id,stock;
    private String name;
    private double rating,price;
    
    public void updateStock(int stock){
        this.stock = stock;
    }
    public void updatePrice(double price){
        this.price = price;
    }
    public void addRating(double rating){
        this.rating = rating;
    }
    public double getAverageRating(){
        return 0;
    }
}
