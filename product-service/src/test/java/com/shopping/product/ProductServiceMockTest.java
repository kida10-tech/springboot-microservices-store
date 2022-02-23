package com.shopping.product;

import com.shopping.product.entity.CategoryEntity;
import com.shopping.product.entity.ProductEntity;
import com.shopping.product.repository.ProductRepository;
import com.shopping.product.service.ProductService;
import com.shopping.product.service.impl.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ProductServiceMockTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach()
    public void setup() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(productRepository);
        ProductEntity computer = ProductEntity.builder()
                .id(1L)
                .name("computer")
                .category(CategoryEntity.builder().id(1L).build())
                .price(Double.parseDouble("1200"))
                .stock(Integer.parseInt("5"))
                .build();

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(computer));
        Mockito.when(productRepository.save(computer)).thenReturn(computer);
    }

    @Test
    public void whenValidGetId_thenReturnProduct() {
        ProductEntity found = productService.getProduct(1L);
        Assertions.assertThat(found.getName()).isEqualTo("computer");
    }

    @Test
    public void whenValidUpdateStock_thenReturnNewStock() {
        ProductEntity newStock = productService.updateStock(1L, Integer.parseInt("5"));
        Assertions.assertThat(newStock.getStock()).isEqualTo(10);
    }
}
