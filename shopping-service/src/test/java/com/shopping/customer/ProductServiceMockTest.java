package com.shopping.customer;

import com.shopping.customer.entity.InvoiceEntity;
import com.shopping.customer.repository.InvoiceRepository;
import com.shopping.customer.service.InvoiceService;
import com.shopping.customer.service.impl.InvoiceServiceImpl;
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
    private InvoiceRepository customerRepository;

    private InvoiceService productService;

    @BeforeEach()
    public void setup() {
        MockitoAnnotations.openMocks(this);
        productService = new InvoiceServiceImpl(customerRepository);
        InvoiceEntity computer = InvoiceEntity.builder()
                .id(1L)
                .firstName("Diego")
                .category(RegionEntity.builder().id(1L).build())
                .price(Double.parseDouble("1200"))
                .stock(Integer.parseInt("5"))
                .build();

        Mockito.when(customerRepository.findById(1L))
                .thenReturn(Optional.of(computer));
        Mockito.when(customerRepository.save(computer)).thenReturn(computer);
    }

    @Test
    public void whenValidGetId_thenReturnProduct() {
        InvoiceEntity found = productService.getProduct(1L);
        Assertions.assertThat(found.getName()).isEqualTo("computer");
    }

    @Test
    public void whenValidUpdateStock_thenReturnNewStock() {
        InvoiceEntity newStock = productService.updateStock(1L, Integer.parseInt("5"));
        Assertions.assertThat(newStock.getStock()).isEqualTo(10);
    }
}
