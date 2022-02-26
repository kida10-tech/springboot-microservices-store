package com.shopping.customer.repository;

import com.shopping.customer.entity.RegionEntity;
import com.shopping.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    CustomerEntity findByNumberId(String numberId);
    List<CustomerEntity> findByLastName(String lastname);
    List<CustomerEntity> findByRegion(RegionEntity region);
}
