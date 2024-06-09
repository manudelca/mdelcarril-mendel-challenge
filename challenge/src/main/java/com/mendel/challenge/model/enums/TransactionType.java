package com.mendel.challenge.model.enums;

public enum TransactionType {
    CARS("CARS"),
    SHOPPING("SHOPPING");

    private final String type;

    TransactionType(String type) {
        this.type = type;
    }

    public String getTransactionType() {
        return type;
    }
}