package com.mendel.challenge.controller;

import com.mendel.challenge.dto.controller.CreateTransactionRequestDTO;
import com.mendel.challenge.dto.controller.CreateTransactionResponseDTO;
import com.mendel.challenge.model.enums.TransactionType;
import com.mendel.challenge.service.TransactionService;
import com.mendel.challenge.dto.service.TransactionDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PutMapping("/transactions/{transactionId}")
    public CreateTransactionResponseDTO CreateTransaction(@PathVariable("transactionId") Long transactionId, @Valid @RequestBody CreateTransactionRequestDTO transaction) {
        TransactionDTO transactionDTO = new TransactionDTO(transactionId,
                    transaction.getAmount(), TransactionType.valueOf(transaction.getType()), transaction.getParentId());
        TransactionDTO response = this.transactionService.CreateTransaction(transactionDTO);
        return new CreateTransactionResponseDTO(response.getId(),
                response.getAmount(),
                response.getType(),
                response.getParentId());
    }
}
