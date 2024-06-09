package com.mendel.challenge.dto.service.exception;

public class ParentTransactionNotFoundException extends RuntimeException {
    public ParentTransactionNotFoundException(Long parentTransactionId, Throwable err) {
        super(String.format("missing parent transaction with id: %d", parentTransactionId), err);
    }
}
