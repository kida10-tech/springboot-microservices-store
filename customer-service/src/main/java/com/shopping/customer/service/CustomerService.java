package com.shopping.customer.service;

import com.shopping.customer.entity.RegionEntity;
import com.shopping.customer.entity.CustomerEntity;

import java.util.List;

public interface CustomerService {

    List<CustomerEntity> findAllCustomers();

    CustomerEntity getCustomer(Long id);

    CustomerEntity createCustomer(CustomerEntity customer);

    CustomerEntity updateCustomer(CustomerEntity customer);

    CustomerEntity deleteCustomer(CustomerEntity customer);

    List<CustomerEntity> findCustomerByRegion(RegionEntity region);


}
