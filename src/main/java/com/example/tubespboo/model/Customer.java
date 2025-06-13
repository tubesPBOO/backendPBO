package com.example.tubespboo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document(collection = "customers")
public class Customer extends User implements UserDetails {

    private String address;
    private String phoneNumber;
    private List<Order> orders = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();

    public Customer() {
        super();
    }

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

    public void ratingTukang(int id, double rate) {

    }

    public void ratingMaterial(int id, double rate) {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(getRole()));
    }

    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Project> getProjects() {
        return this.projects;
    }

    public void addProject(Project project) {
        this.projects.add(project);
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
