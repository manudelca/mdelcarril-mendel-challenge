package com.mendel.challenge.controller;
import com.mendel.challenge.dto.controller.ErrorResponseDTO;
import com.mendel.challenge.dto.service.exception.ParentTransactionIdEqualsTransactionIdException;
import com.mendel.challenge.dto.service.exception.ParentTransactionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->
            errors.add(String.format("%s: %s", ((FieldError) error).getField(), error.getDefaultMessage()))
        );
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String message = "invalid request";
        return new ResponseEntity<>(new ErrorResponseDTO(httpStatus.value(), httpStatus.getReasonPhrase(), message, errors), httpStatus);
    }

    @ExceptionHandler({ParentTransactionNotFoundException.class})
    public ResponseEntity<ErrorResponseDTO> handleParentTransactionNotFoundException(ParentTransactionNotFoundException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        String message = "parent transaction not found";
        return new ResponseEntity<>(new ErrorResponseDTO(httpStatus.value(), httpStatus.getReasonPhrase(), message, errors), httpStatus);
    }

    @ExceptionHandler({ParentTransactionIdEqualsTransactionIdException.class})
    public ResponseEntity<ErrorResponseDTO> handleParentTransactionIdEqualsTransactionIdException(ParentTransactionIdEqualsTransactionIdException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String message = "parent transaction equals transaction id";
        return new ResponseEntity<>(new ErrorResponseDTO(httpStatus.value(), httpStatus.getReasonPhrase(), message, errors), httpStatus);
    }
}