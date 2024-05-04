// Note: Do not write @Enumerated annotation above CountryName in this model.
package com.driver.model;

import javax.persistence.*;

@Entity
@Table
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private CountryName countryName;
    private String code;

    @ManyToOne //Many users leave in 1 single country
    @JoinColumn
    private User user;

    @OneToOne //1 country having only 1 service provider
    @JoinColumn
    private ServiceProvider serviceProvider;

    public Country() {
    }

    public Country(CountryName countryName, String code) {
        this.countryName = countryName;
        this.code = code;
    }

    public Country(int id, CountryName countryName, String code, User user, ServiceProvider serviceProvider) {
        this.id = id;
        this.countryName = countryName;
        this.code = code;
        this.user = user;
        this.serviceProvider = serviceProvider;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CountryName getCountryName() {
        return countryName;
    }

    public void setCountryName(CountryName countryName) {
        this.countryName = countryName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
}
