package com.example.tubespboo.model;

public class Material {
    private int id,stock,ratingCount;
    private String name;
    private double totrating,price;
    
    public void updateStock(int stock){
        this.stock = stock;
    }
    public void updatePrice(double price){
        this.price = price;
    }
    public void addRating(double totrating){
        this.totrating = totrating;
        this.ratingCount++;
    }
    public double getAverageRating(){
        //exception disini ketika dia 0
        return totrating / ratingCount;
    }
}
