package com.shopping.shopping;

import com.shopping.shopping.entity.RegionEntity;
import com.shopping.shopping.entity.CustomerEntity;
import com.shopping.shopping.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
public class CustomerRepositoryMockTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void whenFindByCategory_thenReturnProductList() {

        CustomerEntity product = CustomerEntity.builder()
                .name("computer")
                .category(RegionEntity.builder().id(1l).build())
                .description("it's a computer")
                .stock(Integer.parseInt("10"))
                .price(Double.parseDouble("1090.90"))
                .status("Created")
                .createAt(LocalDate.now()).build();

        customerRepository.save(product);

        List<CustomerEntity> found = customerRepository.findByCategory(product.getCategory());

        Assertions.assertThat(found.size()).isEqualTo(2);

    }
}
