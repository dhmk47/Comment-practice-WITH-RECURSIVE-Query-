package com.practice.comment.service.comment;

import com.practice.comment.domain.comment.Product;
import com.practice.comment.domain.comment.ProductRepository;
import com.practice.comment.handler.aop.exception.ProductException;
import com.practice.comment.handler.aop.exception.ProductErrorResult;
import com.practice.comment.web.dto.CreateProductRequestDto;
import com.practice.comment.web.dto.CreateProductResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final AlertService alertService;
    private final TestService testService;

    public CreateProductResponseDto createProduct(CreateProductRequestDto createProductRequestDto) {
        Product productInfo = createProductRequestDto.toProduct();
        Product productResult = productRepository.findProduct(productInfo);
        int rr = testService.testMethod();
        log.info("test: {}", rr);
        boolean result = false;
        if(productResult != null){
            throw new ProductException(ProductErrorResult.ALREADY_HAS_PRODUCT_EXCEPTION);

        }else {
            result = productRepository.createProduct(productInfo) > 0;

            if(result) {
                alertService.successAlert();

            }else {
                alertService.failedAlert();

            }
        }

        return result ? productInfo.toProductDto() : null;
    }

}