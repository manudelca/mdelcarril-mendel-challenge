package com.mendel.challenge.dto.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetTransactionSumResponseDTO {
    @JsonProperty("sum")
    private final Double sum;

    public Double getSum() {
        return sum;
    }
    public GetTransactionSumResponseDTO(Double sum) {
        this.sum = sum;
    }
}
