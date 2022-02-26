package com.shopping.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.product.entity.CategoryEntity;
import com.shopping.product.entity.ProductEntity;
import com.shopping.product.error.ErrorMessage;
import com.shopping.product.service.ProductService;
import org.apache.catalina.LifecycleState;
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

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductEntity>> productList(@RequestParam(name = "categoryId", required = false) Long categoryId) {
        List<ProductEntity> products;
        if(categoryId == null) {
            products = productService.listAllProducts();
            if(products.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        } else {
            products = productService.findByCategory(CategoryEntity.builder().id(categoryId).build());
            if(products.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProduct(@PathVariable("id") Long id) {
        ProductEntity product = productService.getProduct(id);
        if(product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(@Valid @RequestBody ProductEntity product, BindingResult result) {
        if(result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        ProductEntity newProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable("id") Long id, @RequestBody ProductEntity product) {
        product.setId(id);
        ProductEntity productFound = productService.updateProduct(product);
        if(productFound == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productFound);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductEntity> deleteProduct(@PathVariable("id") Long id) {
        ProductEntity deletedProduct = productService.deleteProduct(id);
        if(deletedProduct == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deletedProduct);
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<ProductEntity> updateStockProduct(@PathVariable("id") Long id,@RequestParam(name = "quantity", required = true) Integer quantity) {
        ProductEntity productStock = productService.updateStock(id, quantity);
        if(productStock == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productStock);
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
