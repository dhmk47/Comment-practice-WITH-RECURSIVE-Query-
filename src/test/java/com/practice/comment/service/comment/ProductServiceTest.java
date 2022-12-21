package com.practice.comment.service.comment;

import com.practice.comment.domain.comment.Product;
import com.practice.comment.domain.comment.ProductRepository;
import com.practice.comment.handler.aop.exception.ProductException;
import com.practice.comment.web.dto.CreateProductRequestDto;
import com.practice.comment.web.dto.CreateProductResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Spy
    private TestService testService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private AlertService alertService;
    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("이미 상품이 있어 AlreadyHasProductException 예외 호출")
    void AlreadyHasProductException호출() throws Exception {
        // given
        CreateProductRequestDto productDto = new CreateProductRequestDto("세탁기");
        Product product = productDto.toProduct();
        when(productRepository.findProduct(product)).thenReturn(product);


        // when
//        assertThatThrownBy(() -> {
//            productService.createProduct(product);
//        }).isExactlyInstanceOf(AlreadyHasProductException.class)
//                .isInstanceOf(AlreadyHasProductException.class)
//                .hasMessage("이미 등록되어 있는 상품");

        assertThatExceptionOfType(ProductException.class)
                .isThrownBy(() -> {
                    productService.createProduct(productDto);
                });

        // then
    }

    @Test
    void 상품등록실패() throws Exception {
        // given
        CreateProductRequestDto productDto = new CreateProductRequestDto("청소기");
        Product product = productDto.toProduct();

        when(productRepository.findProduct(product)).thenReturn(null);
        when(productRepository.createProduct(product)).thenReturn(0);

        // when
        CreateProductResponseDto result = productService.createProduct(productDto);

        // then
        verify(alertService, times(1)).failedAlert();
        assertThat(result).isNull();
    }

    @Test
    void 상품등록성공() throws Exception {
        // given
        CreateProductRequestDto productDto = new CreateProductRequestDto("청소기");
        Product product = productDto.toProduct();

        when(productRepository.findProduct(product)).thenReturn(null);
        when(productRepository.createProduct(product)).thenReturn(1);

        // when
        CreateProductResponseDto result = productService.createProduct(productDto);

        // then
        verify(alertService, times(1)).successAlert();
        assertThat(result.getClass()).isEqualTo(CreateProductResponseDto.class);
    }
}