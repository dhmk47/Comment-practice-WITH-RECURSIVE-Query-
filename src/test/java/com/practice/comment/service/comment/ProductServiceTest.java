package com.practice.comment.service.comment;

import com.practice.comment.domain.comment.Product;
import com.practice.comment.domain.comment.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

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
    @DisplayName("successAlert() 호출")
    void createProductTest() throws Exception {
        Product product = new Product();

        when(productRepository.findProduct(product)).thenReturn(null);
        when(productRepository.createProduct(product)).thenReturn(1);
        when(testService.testMethod()).thenReturn(1);

        productService.createProduct(product);

        verify(alertService, times(1)).successAlert();
//        verify(alertService, times(1)).failedAlert();
    }
}