package com.mendel.challenge.dto.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mendel.challenge.model.enums.TransactionType;

public class CreateTransactionResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("type")
    private TransactionType type;

    @JsonProperty("parent_id")
    private Long parentId;


    public CreateTransactionResponseDTO(Long id, Double amount, TransactionType type, Long parentId) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public TransactionType getType() {
        return type;
    }
    public void setType(TransactionType type) {
        this.type = type;
    }
    public Long getParentId() {
        return parentId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
