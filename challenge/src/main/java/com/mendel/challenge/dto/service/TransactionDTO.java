package com.mendel.challenge.dto.service;

import com.mendel.challenge.model.Transaction;
import com.mendel.challenge.model.enums.TransactionType;

import java.util.Objects;

public class TransactionDTO {
    private final Long id;
    private final Double amount;
    private final TransactionType type;
    private final Long parentId;

    public TransactionDTO(Long id, Double amount, TransactionType type, Long parentId) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.parentId = parentId;
    }

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.type = transaction.getType();
        this.parentId = transaction.getParentId();
    }

    public Transaction buildTransactionFromDTO() {
        return new Transaction(id, amount, type, parentId);
    }

    public Long getId() {
        return id;
    }
    public Double getAmount() {
        return amount;
    }
    public TransactionType getType() {
        return type;
    }
    public Long getParentId() {
        return parentId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        TransactionDTO transaction = (TransactionDTO) o;
        return Objects.equals(id, transaction.id) && Objects.equals(amount, transaction.amount) && Objects.equals(type, transaction.type) && Objects.equals(parentId, transaction.parentId);
    }
}
