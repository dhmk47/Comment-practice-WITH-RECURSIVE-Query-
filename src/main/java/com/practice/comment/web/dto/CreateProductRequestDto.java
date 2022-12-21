package com.practice.comment.web.dto;

import com.practice.comment.domain.comment.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateProductRequestDto {
    private String productName;

    public Product toProduct() {
        return Product.builder()
                .product_name(productName)
                .build();
    }
}
