package com.shopping.shopping.client;

import com.shopping.shopping.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("api/products/{id}")
    ResponseEntity<Product> getProduct(@PathVariable("id") Long id);

    @GetMapping("api/products/{id}/stock")
    ResponseEntity<Product> updateStockProduct(@PathVariable("id") Long id,@RequestParam(name = "quantity", required = true) Integer quantity);
}
