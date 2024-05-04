package com.driver.services.impl;

import com.driver.model.Admin;
import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.repository.AdminRepository;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository1;

    @Autowired
    ServiceProviderRepository serviceProviderRepository1;

    @Autowired
    CountryRepository countryRepository1;

    @Override
    public Admin register(String username, String password) {
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        adminRepository1.save(admin);
        return admin;
    }

    @Override
    public Admin addServiceProvider(int adminId, String providerName) {
        Optional<Admin> optionalAdmin = adminRepository1.findById(adminId);
        Admin admin = optionalAdmin.get();

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setName(providerName);
        serviceProvider.setAdmin(admin);
        List<ServiceProvider> serviceProviderList = admin.getServiceProviders();
        serviceProviderList.add(serviceProvider);

        admin.setServiceProviders(serviceProviderList);

        adminRepository1.save(admin);
        return admin;
    }

    @Override
    public ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception{

        String str = countryName.toUpperCase();
        if(!str.equals("IND") && !str.equals("JPN") && !str.equals("AUS") && !str.equals("CHI") && !str.equals("USA")){
            throw new Exception("Country not found");
        }

        Optional<ServiceProvider> optionalServiceProvider = serviceProviderRepository1.findById(serviceProviderId);
        ServiceProvider serviceProvider = optionalServiceProvider.get();
        Country country = new Country(CountryName.valueOf(str), CountryName.valueOf(str).toCode());

        country.setServiceProvider(serviceProvider);
        serviceProvider.getCountryList().add(country);
        serviceProviderRepository1.save(serviceProvider);
        return serviceProvider;
    }
}
