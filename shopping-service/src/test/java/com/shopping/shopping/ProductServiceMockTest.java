package com.shopping.shopping;

import com.shopping.shopping.entity.InvoiceEntity;
import com.shopping.shopping.entity.InvoiceItemsEntity;
import com.shopping.shopping.repository.InvoiceRepository;
import com.shopping.shopping.service.InvoiceService;
import com.shopping.shopping.service.impl.InvoiceServiceImpl;
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
    private InvoiceRepository productRepository;

    private InvoiceService productService;

    @BeforeEach()
    public void setup() {
        MockitoAnnotations.openMocks(this);
        productService = new InvoiceServiceImpl(productRepository);
        InvoiceItemsEntity computer = InvoiceItemsEntity.builder()
                .id(1L)
                .name("computer")
                .category(InvoiceEntity.builder().id(1L).build())
                .price(Double.parseDouble("1200"))
                .stock(Integer.parseInt("5"))
                .build();

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(computer));
        Mockito.when(productRepository.save(computer)).thenReturn(computer);
    }

    @Test
    public void whenValidGetId_thenReturnProduct() {
        InvoiceItemsEntity found = productService.getProduct(1L);
        Assertions.assertThat(found.getName()).isEqualTo("computer");
    }

    @Test
    public void whenValidUpdateStock_thenReturnNewStock() {
        InvoiceItemsEntity newStock = productService.updateStock(1L, Integer.parseInt("5"));
        Assertions.assertThat(newStock.getStock()).isEqualTo(10);
    }
}
