package com.example.tubespboo.model;

import com.example.tubespboo.interfacee.Rateable;
public class Tukang implements Rateable{
    private int id;
    private String name;
    private boolean availability;
    private double totrating;
    private int ratingCount;
    private String phoneNumber;
    
    public void setName(String name){
        this.name = name;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setAvailability(boolean availability){
        this.availability = availability;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public boolean getAvailability(){
        return availability;
    }
    public String getName(){
        return name;
    }
    public int getId(){
        return id;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }


    @Override
    public void addRating(double rating) {
        if(rating >=1 && rating <= 5){
            totrating += rating;
            ratingCount++;
        }else{
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
    }

    @Override
    public double getAverage() {
        if (ratingCount==0){
            throw new ArithmeticException("No ratings yet.");
        }else{
            return totrating/ratingCount;
        }
    }
}