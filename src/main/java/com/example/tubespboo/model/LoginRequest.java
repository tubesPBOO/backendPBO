package com.example.tubespboo.model;

public class LoginRequest {
    private String name;
    private String pass;

    public LoginRequest() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
