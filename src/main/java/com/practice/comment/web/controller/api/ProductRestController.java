package com.practice.comment.web.controller.api;

import com.practice.comment.handler.aop.exception.ProductException;
import com.practice.comment.service.comment.ProductService;
import com.practice.comment.web.dto.CreateProductRequestDto;
import com.practice.comment.web.dto.CreateProductResponseDto;
import com.practice.comment.web.dto.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    @PostMapping("")
    public ResponseEntity<?> createProduct(@RequestBody CreateProductRequestDto createProductRequestDto) {
        CreateProductResponseDto product = productService.createProduct(createProductRequestDto);

        return ResponseEntity.ok(product);
    }

    @GetMapping("/{productName}")
    public ResponseEntity<?> getProduct(@PathVariable String productName, @RequestBody CreateProductRequestDto createProductRequestDto) {
        log.info("Check: {}", createProductRequestDto);
        if(!productName.equals("세탁기")) {
            return ResponseEntity.badRequest().body("failed");
        }
        return ResponseEntity.ok("success");
    }

}