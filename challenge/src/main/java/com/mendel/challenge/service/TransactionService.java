package com.mendel.challenge.service;

import com.mendel.challenge.dto.service.TransactionDTO;

public interface TransactionService {

    TransactionDTO CreateTransaction(TransactionDTO transactionRequest);
}
