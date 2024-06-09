package com.mendel.challenge.dto.controller;


import java.util.List;

public class ErrorResponseDTO {
    private final Integer status;
    private final String error;
    private final String message;
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
