package com.shopping.product.service.impl;

import com.shopping.product.entity.CategoryEntity;
import com.shopping.product.entity.ProductEntity;
import com.shopping.product.repository.ProductRepository;
import com.shopping.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;

    @Override
    public List<ProductEntity> listAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public ProductEntity getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public ProductEntity createProduct(ProductEntity product) {
        product.setStatus("Created");
        product.setCreateAt(LocalDate.now());

        return productRepository.save(product);
    }

    @Override
    public ProductEntity updateProduct(ProductEntity product) {
        ProductEntity found = getProduct(product.getId());
        if(found == null) {
            return null;
        }
        found.setName(product.getName());
        found.setDescription(product.getDescription());
        found.setCategory(product.getCategory());
        found.setPrice(product.getPrice());

        return productRepository.save(found);
    }

    @Override
    public ProductEntity deleteProduct(Long id) {
        ProductEntity found = getProduct(id);
        if(found == null) {
            return null;
        }
        found.setStatus("Deleted");
        return productRepository.save(found);
    }

    @Override
    public List<ProductEntity> findByCategory(CategoryEntity category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public ProductEntity updateStock(Long id, Integer quantity) {
        ProductEntity found = getProduct(id);
        if(found == null) {
            return null;
        }
        Integer stock = found.getStock() + quantity;
        found.setStock(stock);
        return productRepository.save(found);
    }
}
