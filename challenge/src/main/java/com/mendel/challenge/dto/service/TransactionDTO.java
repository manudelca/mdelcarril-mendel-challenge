package com.mendel.challenge.dto.service;

import com.mendel.challenge.model.enums.TransactionType;

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
}
