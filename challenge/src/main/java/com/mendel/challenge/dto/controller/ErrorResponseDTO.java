package com.mendel.challenge.dto.controller;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ErrorResponseDTO {

    @JsonProperty("status")
    private final Integer status;
    @JsonProperty("error")
    private final String error;
    @JsonProperty("message")
    private final String message;
    @JsonProperty("causes")
    private final List<String> causes;


    public ErrorResponseDTO(Integer status, String error, String message, List<String> causes) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.causes = causes;
    }

    public Integer getStatus() {
        return status;
    }
    public String getError() {
        return error;
    }
    public String getMessage() {
        return message;
    }
    public List<String> getCauses() {
        return causes;
    }
}
