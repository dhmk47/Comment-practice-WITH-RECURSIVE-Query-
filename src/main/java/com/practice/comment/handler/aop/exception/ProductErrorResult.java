package com.practice.comment.handler.aop.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum ProductErrorResult {
    ALREADY_HAS_PRODUCT_EXCEPTION(HttpStatus.BAD_REQUEST, "Already Has Product Exception");

    private final HttpStatus httpStatus;
    private final String message;
}