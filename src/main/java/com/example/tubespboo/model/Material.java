package com.example.tubespboo.model;

public class Material implements rateable {
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
        if(totrating >= 1 && totrating <= 5 ){
            this.totrating = totrating;
            this.ratingCount++;
        }else{
            System.out.println("Rating harus antara 1 dan 5.");
        } 
    }
    public double getAverageRating(){
        //exception disini ketika dia 0
        return totrating / ratingCount;
    }
}
