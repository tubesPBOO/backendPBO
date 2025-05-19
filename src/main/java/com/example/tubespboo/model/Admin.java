package com.example.tubespboo.model;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document(collection = "admins") 
public class Admin extends User implements UserDetails{
   public Admin() {
        super();
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
}
