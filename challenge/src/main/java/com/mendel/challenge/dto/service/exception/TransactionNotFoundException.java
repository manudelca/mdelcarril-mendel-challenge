package com.mendel.challenge.dto.service.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(Long id, Throwable err) {
        super(String.format("missing transaction with id: %d", id), err);
    }
}
