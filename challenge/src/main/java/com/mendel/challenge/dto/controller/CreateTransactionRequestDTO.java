package com.mendel.challenge.dto.controller;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.mendel.challenge.model.enums.TransactionType;
import com.mendel.challenge.util.ValueOfEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreateTransactionRequestDTO {

    @Min(value = 0, message = "minimum amount is zero")
    @JsonProperty("amount")
    private Double amount;

    @NotNull(message = "transaction type invalid")
    @ValueOfEnum(enumClass = TransactionType.class)
    @JsonProperty("type")
    private String type;

    @JsonProperty("parent_id")
    private Long parentId;


    public CreateTransactionRequestDTO(Double amount, String type, Long parentId) {
        this.amount = amount;
        this.type = type.toUpperCase();
        this.parentId = parentId;
    }

    public Double getAmount() {
        return amount;
    }
    public String getType() {
        return type;
    }
    public Long getParentId() {
        return parentId;
    }
}
