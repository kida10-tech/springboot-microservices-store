package com.shopping.shopping;

import com.shopping.shopping.entity.InvoiceEntity;
import com.shopping.shopping.entity.InvoiceItemsEntity;
import com.shopping.shopping.repository.InvoiceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
public class ProductRepositoryMockTest {

    @Autowired
    private InvoiceRepository productRepository;

    @Test
    public void whenFindByCategory_thenReturnProductList() {

        InvoiceItemsEntity product = InvoiceItemsEntity.builder()
                .name("computer")
                .category(InvoiceEntity.builder().id(1l).build())
                .description("it's a computer")
                .stock(Integer.parseInt("10"))
                .price(Double.parseDouble("1090.90"))
                .status("Created")
                .createAt(LocalDate.now()).build();

        productRepository.save(product);

        List<InvoiceItemsEntity> found = productRepository.findByCategory(product.getCategory());

        Assertions.assertThat(found.size()).isEqualTo(2);

    }
}
