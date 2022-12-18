package com.practice.comment.service.comment;

import com.practice.comment.domain.comment.Product;
import com.practice.comment.domain.comment.ProductRepository;
import com.practice.comment.handler.aop.AlreadyHasProductException;
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

    public void createProduct(Product productInfo) throws Exception {
        Product product = productRepository.findProduct(productInfo);
        int rr = testService.testMethod();
        log.info("test: {}", rr);
        if(product != null){
            throw new AlreadyHasProductException();

        }else {
            boolean result = productRepository.createProduct(productInfo) > 0;

            if(result) {
                alertService.successAlert();

            }else {
                alertService.failedAlert();

            }
        }

    }
}