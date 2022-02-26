package com.shopping.shopping;

import com.shopping.shopping.entity.RegionEntity;
import com.shopping.shopping.entity.CustomerEntity;
import com.shopping.shopping.repository.CustomerRepository;
import com.shopping.shopping.service.CustomerService;
import com.shopping.shopping.service.impl.CustomerServiceImpl;
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
    private CustomerRepository customerRepository;

    private CustomerService productService;

    @BeforeEach()
    public void setup() {
        MockitoAnnotations.openMocks(this);
        productService = new CustomerServiceImpl(customerRepository);
        CustomerEntity computer = CustomerEntity.builder()
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
        CustomerEntity found = productService.getProduct(1L);
        Assertions.assertThat(found.getName()).isEqualTo("computer");
    }

    @Test
    public void whenValidUpdateStock_thenReturnNewStock() {
        CustomerEntity newStock = productService.updateStock(1L, Integer.parseInt("5"));
        Assertions.assertThat(newStock.getStock()).isEqualTo(10);
    }
}
