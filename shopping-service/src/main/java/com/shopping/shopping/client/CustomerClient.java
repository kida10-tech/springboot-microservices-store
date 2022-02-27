package com.shopping.shopping.client;

import com.shopping.shopping.model.Customer;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
@CircuitBreaker(name = "customer-service", fallbackMethod = "null")
public interface CustomerClient {

    @GetMapping("api/customers/{id}")
    ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id);
}
