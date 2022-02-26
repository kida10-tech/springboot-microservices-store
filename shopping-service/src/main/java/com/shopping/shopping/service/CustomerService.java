package com.shopping.shopping.service;

import com.shopping.shopping.entity.RegionEntity;
import com.shopping.shopping.entity.CustomerEntity;

import java.util.List;

public interface CustomerService {

    List<CustomerEntity> findAllCustomers();

    CustomerEntity getCustomer(Long id);

    CustomerEntity createCustomer(CustomerEntity customer);

    CustomerEntity updateCustomer(CustomerEntity customer);

    CustomerEntity deleteCustomer(CustomerEntity customer);

    List<CustomerEntity> findCustomerByRegion(RegionEntity region);


}
