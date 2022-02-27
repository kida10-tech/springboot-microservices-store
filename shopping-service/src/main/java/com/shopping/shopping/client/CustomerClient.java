package com.shopping.shopping.client;

import com.shopping.shopping.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service", fallback = CustomerResilience4JFallback.class)
public interface CustomerClient {

    @GetMapping("api/customers/{id}")
    ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id);
}
