package com.shopping.customer;

import com.shopping.customer.entity.InvoiceEntity;
import com.shopping.customer.repository.InvoiceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
public class InvoiceRepositoryMockTest {

    @Autowired
    private InvoiceRepository customerRepository;

    @Test
    public void whenFindByCategory_thenReturnProductList() {

        InvoiceEntity product = InvoiceEntity.builder()
                .name("computer")
                .category(RegionEntity.builder().id(1l).build())
                .description("it's a computer")
                .stock(Integer.parseInt("10"))
                .price(Double.parseDouble("1090.90"))
                .status("Created")
                .createAt(LocalDate.now()).build();

        customerRepository.save(product);

        List<InvoiceEntity> found = customerRepository.findByCategory(product.getCategory());

        Assertions.assertThat(found.size()).isEqualTo(2);

    }
}
