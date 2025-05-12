package com.example.tubespboo.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customers") 
public class Customer extends User {

    private String address;
    private String phoneNumber;

    public Customer() {}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    
    public void pesan() {
        
    }

    public void lihatPesanan() {
        
    }

    public void updateContactInfo(String address, String phoneNumber) {
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void viewDashboard() {
        
    }

    public void ratingTukang(int id, double rate) {
       
    }

    public void ratingMaterial(int id, double rate) {
        
    }
}
