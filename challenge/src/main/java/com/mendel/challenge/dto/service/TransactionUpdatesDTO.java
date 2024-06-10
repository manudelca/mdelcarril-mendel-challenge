package com.mendel.challenge.dto.service;

public class TransactionUpdatesDTO {
    private Long id;

    private Double amount;

    public TransactionUpdatesDTO(Long id, Double amount) {
        this.id = id;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }
}
