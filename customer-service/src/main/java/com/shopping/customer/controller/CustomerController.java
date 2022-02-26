package com.shopping.customer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.customer.entity.RegionEntity;
import com.shopping.customer.entity.CustomerEntity;
import com.shopping.customer.error.ErrorMessage;
import com.shopping.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerEntity>> listAllCustomers(@RequestParam(name = "regionId", required = false) Long regionId) {
        List<CustomerEntity> customers;
        if(regionId == null) {
            customers = customerService.findAllCustomers();
            if(customers.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        } else {
            customers = customerService.findCustomerByRegion(RegionEntity.builder().id(regionId).build());
            if(customers.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerEntity> getCustomer(@PathVariable("id") Long id) {
        log.info("Fetching Customer with id {}", id);
        CustomerEntity customer = customerService.getCustomer(id);
        if(customer == null) {
            log.error("Customer with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<CustomerEntity> createCustomer(@Valid @RequestBody CustomerEntity customer, BindingResult result) {

        log.info("Creating Customer: {}", customer);

        if(result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        CustomerEntity newCustomer = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerEntity> updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerEntity customer) {

        log.info("Updating Customer with id {}", id);

        CustomerEntity customerFound = customerService.updateCustomer(customer);
        if(customerFound == null) {
            log.error("Unable to update. Customer with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customerFound);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerEntity> deleteCustomer(@PathVariable("id") Long id) {

        log.info("Fetching & Deleting Customer with id {}", id);
        CustomerEntity customer = customerService.getCustomer(id);
        if(customer == null) {
            log.error("Unable to delete. Customer with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        customer = customerService.deleteCustomer(customer);
        return ResponseEntity.ok(customer);
    }

    private String formatMessage(BindingResult result) {
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String,String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;})
                .collect(Collectors.toList());

        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;

    }
}
