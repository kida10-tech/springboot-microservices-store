package com.shopping.product;

import com.shopping.product.entity.CategoryEntity;
import com.shopping.product.entity.ProductEntity;
import com.shopping.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.Temporal;
import java.time.LocalDate;
import java.util.List;

@DataJpaTest
public class ProductRepositoryMockTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenFindByCategory_thenReturnProductList() {

        ProductEntity product = ProductEntity.builder()
                .name("computer")
                .category(CategoryEntity.builder().id(1l).build())
                .description("it's a computer")
                .stock(Integer.parseInt("10"))
                .price(Double.parseDouble("1090.90"))
                .status("Created")
                .createAt(LocalDate.now()).build();

        productRepository.save(product);

        List<ProductEntity> found = productRepository.findByCategory(product.getCategory());

        Assertions.assertThat(found.size()).isEqualTo(2);

    }
}
