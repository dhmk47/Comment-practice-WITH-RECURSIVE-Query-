package com.practice.comment.domain.comment;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductRepository {
    public int createProduct(Product product) throws Exception;
    public Product findProduct(Product product);
}
