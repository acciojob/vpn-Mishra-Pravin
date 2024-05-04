package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class ServiceProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn
    private Admin admin;

    @ManyToOne
    @JoinColumn
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL)
    private List<Connection> connectionList = new ArrayList<>();

    @OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL)
    private List<Country> countryList = new ArrayList<>();

    public ServiceProvider() {
    }

    public ServiceProvider(Integer id, String name, Admin admin, List<User> users, List<Connection> connectionList, List<Country> countryList) {
        this.id = id;
        this.name = name;
        this.admin = admin;
        this.users = users;
        this.connectionList = connectionList;
        this.countryList = countryList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Connection> getConnectionList() {
        return connectionList;
    }

    public void setConnectionList(List<Connection> connectionList) {
        this.connectionList = connectionList;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }
}
