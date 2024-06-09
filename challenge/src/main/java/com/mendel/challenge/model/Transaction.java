package com.mendel.challenge.model;

import com.mendel.challenge.model.enums.TransactionType;

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
}
