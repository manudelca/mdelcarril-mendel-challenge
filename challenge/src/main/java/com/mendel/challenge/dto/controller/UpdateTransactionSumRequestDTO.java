package com.mendel.challenge.dto.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class UpdateTransactionSumRequestDTO {
    @NotNull(message = "id invalid")
    @JsonProperty("id")
    private Long id;

    @NotNull(message = "sum_diff invalid")
    @JsonProperty("sum_diff")
    private Double sumDiff;

    public UpdateTransactionSumRequestDTO(Long id, Double sumDiff) {
        this.id = id;
        this.sumDiff = sumDiff;
    }

    public Long getId() {
        return id;
    }

    public Double getSumDiff() {
        return sumDiff;
    }
}
