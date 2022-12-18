package com.practice.comment.handler.aop;

public class AlreadyHasProductException extends Exception {
    public AlreadyHasProductException() {
        super("failed");
    }
}