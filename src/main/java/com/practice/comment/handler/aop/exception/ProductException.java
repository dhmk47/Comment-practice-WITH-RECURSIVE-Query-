package com.practice.comment.handler.aop.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ProductException extends RuntimeException {
    private final ProductErrorResult productErrorResult;
}