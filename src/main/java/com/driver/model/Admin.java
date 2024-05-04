package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<ServiceProvider> serviceProviders = new ArrayList<>();

    public Admin() {
    }

    public Admin(Integer id, String username, String password, List<ServiceProvider> serviceProviders) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.serviceProviders = serviceProviders;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ServiceProvider> getServiceProviders() {
        return serviceProviders;
    }

    public void setServiceProviders(List<ServiceProvider> serviceProviders) {
        this.serviceProviders = serviceProviders;
    }
}
