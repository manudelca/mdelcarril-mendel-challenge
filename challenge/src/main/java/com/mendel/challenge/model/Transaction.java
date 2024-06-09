package com.mendel.challenge.model;

import com.mendel.challenge.model.enums.TransactionType;

import java.util.Objects;

public class Transaction {
    private final Long id;
    private final Double amount;
    private final String type;
    private final Long parentId;

    public Transaction(Long id, Double amount, TransactionType type, Long parentId) {
        this.id = id;
        this.amount = amount;
        this.type = type.getTransactionType();
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }
    public Double getAmount() {
        return amount;
    }
    public TransactionType getType() {
        return TransactionType.valueOf(type);
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
        Transaction transaction = (Transaction) o;
        return Objects.equals(id, transaction.id) && Objects.equals(amount, transaction.amount) && Objects.equals(type, transaction.type) && Objects.equals(parentId, transaction.parentId);
    }
}
