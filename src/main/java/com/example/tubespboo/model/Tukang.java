package com.example.tubespboo.model;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.tubespboo.interfacee.Rateable;

@Document(collection = "tukang") 
public class Tukang extends User implements Rateable, UserDetails{
    private boolean availability;
    private double totrating;
    private int ratingCount;
    private String phoneNumber;
    

    public void setAvailability(boolean availability){
        this.availability = availability;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public boolean getAvailability(){
        return availability;
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
            return 0.0;
        }else{
            return totrating/ratingCount;
        }
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return List.of(new SimpleGrantedAuthority(getRole()));
    }
    @Override
    public String getPassword() {
        return super.getPassword();
    }
    @Override
    public String getUsername() {
        return super.getName();
    }
}