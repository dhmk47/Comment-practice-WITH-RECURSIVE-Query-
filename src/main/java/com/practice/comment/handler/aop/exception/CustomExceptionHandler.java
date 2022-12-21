package com.practice.comment.handler.aop.exception;

import com.practice.comment.web.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({ProductException.class})
    public ResponseEntity<?> alreadyHasProductException(ProductException exception) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>들어옴");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(makeErrorResponseEntity(exception.getProductErrorResult()));
    }

    private ResponseEntity<?> makeErrorResponseEntity(ProductErrorResult errorResult) {
        return ResponseEntity.status(errorResult.getHttpStatus())
                .body(new ErrorResponse(errorResult.name(), errorResult.getMessage()));
    }
}
