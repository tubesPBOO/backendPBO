package com.example.tubespboo.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "projects")
public class Project {
    @Id
    private Integer id;

    private String name;
    private Integer jumTukang;
    private List<Tukang> listTukang = new ArrayList<>();
    private String deskripsi;
    private String alamatKota;
    private Integer durasi;
    private Integer price;
    private String status;
    private String customerId;
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public String getName() {
        return name;
    }
    public void addTukang(Tukang tukang) {
        this.listTukang.add(tukang);
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCustomer(String customerID){
        this.customerId = customerID;
    }
    public String getCustomer(){
        return customerId;
    }
    public int getJumTukang() {
        return jumTukang;
    }

    public void setJumTukang(int jumTukang) {
        this.jumTukang = jumTukang;
    }

    public List<Tukang> getListTukang() {
        return listTukang;
    }

    public void setListTukang(List<Tukang> listTukang) {
        this.listTukang = listTukang;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getAlamatKota() {
        return alamatKota;
    }

    public void setAlamatKota(String alamatKota) {
        this.alamatKota = alamatKota;
    }

    public int getDurasi() {
        return durasi;
    }

    public void setDurasi(int durasi) {
        this.durasi = durasi;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalPrice() {
        return jumTukang * durasi * 150000 + 250000;
    }
}
