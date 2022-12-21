package com.practice.comment.domain.comment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@WebAppConfiguration
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void 상품등록성공() throws Exception {
        // given
        Product product = Product.builder()
                .product_name("에어컨")
                        .build();

        // when
        int result = productRepository.createProduct(product);

        // then
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void 상품조회실패() throws Exception {
        // given
        Product product = Product.builder()
                .product_name("에어컨")
                .build();

        // when
        Product result = productRepository.findProduct(product);

        // then
        assertThat(result).isNull();
    }

    @Test
    public void 상품조회성공() throws Exception {
        // given
        Product product = Product.builder()
               .product_name("냉장고")
                       .build();

        // when
        Product result = productRepository.findProduct(product);

        // then
        assertThat(result.getProduct_name()).isEqualTo("냉장고");
    }
}