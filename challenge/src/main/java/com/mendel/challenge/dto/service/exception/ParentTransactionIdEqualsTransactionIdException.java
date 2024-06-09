package com.mendel.challenge.dto.service.exception;

public class ParentTransactionIdEqualsTransactionIdException extends RuntimeException {
    public ParentTransactionIdEqualsTransactionIdException(Long transactionId, Long parentTransactionId, Throwable err) {
        super(String.format("transaction id can't be equal to parent transaction with id. transaction id: %d. parent transaction id: %d", transactionId, parentTransactionId), err);
    }
}
