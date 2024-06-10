package com.mendel.challenge.controller;

import com.mendel.challenge.dto.controller.UpdateTransactionSumRequestDTO;
import com.mendel.challenge.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionConsumerController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionConsumerController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/consumer/transactions/updates/sum")
    public void UpdateTransactionSum(@RequestBody UpdateTransactionSumRequestDTO request) {
        this.transactionService.UpdateTransactionSum(request.getId(), request.getSumDiff());
    }
}
