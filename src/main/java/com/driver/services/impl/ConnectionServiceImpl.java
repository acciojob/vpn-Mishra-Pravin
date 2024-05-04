package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ConnectionRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    @Autowired
    UserRepository userRepository2;
    @Autowired
    ServiceProviderRepository serviceProviderRepository2;
    @Autowired
    ConnectionRepository connectionRepository2;

    @Override
    public User connect(int userId, String countryName) throws Exception{
        Optional<User> optionalUser = userRepository2.findById(userId);
        User user = optionalUser.get();
        if(user.getMaskedIp() != null) {
            throw new Exception("Already connected");
        }

        if (countryName.equalsIgnoreCase(user.getOriginalCountry().getCountryName().toString())){
            return user;
        }

        if(user.getServiceProviderList() == null) throw new Exception("Unable to connect");

        List<ServiceProvider> serviceProviderList = user.getServiceProviderList();
        ServiceProvider serviceProviderWithLowestId = null;
        int lowestId = Integer.MAX_VALUE;
        Country country = null;
        for (ServiceProvider serviceProvider : serviceProviderList){
            List<Country> countryList = serviceProvider.getCountryList();
            for (Country country1 : countryList){
                if (countryName.equalsIgnoreCase(country1.getCountryName().toString()) && lowestId>serviceProvider.getId()){
                    lowestId = serviceProvider.getId();
                    serviceProviderWithLowestId = serviceProvider;
                    country = country1;
                }
            }
        }

        if (serviceProviderWithLowestId != null){
            Connection connection = new Connection();
            connection.setUser(user);
            connection.setServiceProvider(serviceProviderWithLowestId);
            user.setMaskedIp(country.getCode() + "." + serviceProviderWithLowestId.getId() + "." + userId);
            user.setConnected(true);
            user.getConnectionList().add(connection);
            serviceProviderWithLowestId.getConnectionList().add(connection);
            userRepository2.save(user);
            serviceProviderRepository2.save(serviceProviderWithLowestId);
        }
        else{
            throw new Exception("Unable to connect");
        }
        return user;

    }
    @Override
    public User disconnect(int userId) throws Exception {
        User user = userRepository2.findById(userId).get();
        if (!user.getConnected()){
            throw new Exception("Already disconnected");
        }
        user.setMaskedIp(null);
        user.setConnected(false);
        userRepository2.save(user);
        return user;
    }
    @Override
    public User communicate(int senderId, int receiverId) throws Exception {
        User sender = userRepository2.findById(senderId).get();
        User receiver = userRepository2.findById(receiverId).get();
        if (receiver.getMaskedIp()!=null){
            String maskedIp = receiver.getMaskedIp();
            String code = maskedIp.substring(0,3);
            code = code.toUpperCase();
            if (code.equals(sender.getOriginalCountry().getCode())) return sender;
            String countryName = "";
            CountryName[] countryNames = CountryName.values();
            for(CountryName countryName1 : countryNames){
                if (countryName1.toCode().toString().equals(code)){
                    countryName = countryName1.toString();
                }
            }
            try {
                sender = connect(senderId,countryName);
            }catch (Exception e){
                throw new Exception("Cannot establish communication");
            }
            if (!sender.getConnected()){
                throw new Exception("Cannot establish communication");
            }
            return sender;
        }
        if (sender.getOriginalCountry().equals(receiver.getOriginalCountry())){
            return sender;
        }
        String countryName = receiver.getOriginalCountry().getCountryName().toString();
        try {
            sender = connect(senderId,countryName);
        }catch (Exception e){
            if (!sender.getConnected()) throw new Exception("Cannot establish communication");
        }
        return sender;
    }
}
