package com.practice.comment.domain.comment;

import com.practice.comment.web.dto.CreateProductResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class Product {
    private int product_code;
    private String product_name;

    public CreateProductResponseDto toProductDto() {
        return CreateProductResponseDto.builder()
                .productCode(product_code)
                .productName(product_name)
                .build();
    }
}
