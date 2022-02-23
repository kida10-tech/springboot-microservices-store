package com.shopping.product.service;

import com.shopping.product.entity.CategoryEntity;
import com.shopping.product.entity.ProductEntity;

import java.util.List;

public interface ProductService {

    List<ProductEntity> listAllProducts();
    ProductEntity getProduct(Long id);

    ProductEntity createProduct(ProductEntity product);
    ProductEntity updateProduct(ProductEntity product);
    ProductEntity deleteProduct(Long id);

    List<ProductEntity> findByCategory(CategoryEntity category);
    ProductEntity updateStock(Long id, Integer quantity);

}
