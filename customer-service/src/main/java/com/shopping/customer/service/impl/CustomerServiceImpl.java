package com.shopping.customer.service.impl;

import com.shopping.customer.entity.RegionEntity;
import com.shopping.customer.entity.CustomerEntity;
import com.shopping.customer.repository.CustomerRepository;
import com.shopping.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public List<CustomerEntity> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public CustomerEntity getCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public CustomerEntity createCustomer(CustomerEntity customer) {
        CustomerEntity newCustomer = customerRepository.findByNumberId(customer.getNumberId());
        if(newCustomer != null) {
            return newCustomer;
        }
        customer.setState("Created");
        newCustomer = customerRepository.save(customer);
        return newCustomer;
    }

    @Override
    public CustomerEntity updateCustomer(CustomerEntity customer) {
        CustomerEntity found = getCustomer(customer.getId());
        if(found == null) {
            return null;
        }
        found.setFirstName(customer.getFirstName());
        found.setLastName(customer.getLastName());
        found.setEmail(customer.getEmail());
        found.setPhotoUrl(customer.getPhotoUrl());

        return customerRepository.save(found);
    }

    @Override
    public CustomerEntity deleteCustomer(CustomerEntity customer) {
        CustomerEntity found = getCustomer(customer.getId());
        if(found == null) {
            return null;
        }
        found.setState("Deleted");
        return customerRepository.save(found);
    }

    @Override
    public List<CustomerEntity> findCustomerByRegion(RegionEntity region) {
        return customerRepository.findByRegion(region);
    }

}
