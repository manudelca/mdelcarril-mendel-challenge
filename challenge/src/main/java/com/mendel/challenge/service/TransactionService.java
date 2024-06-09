package com.mendel.challenge.service;

import com.mendel.challenge.dto.service.TransactionDTO;
import com.mendel.challenge.model.enums.TransactionType;
import com.mendel.challenge.util.PagedResultsDTO;

public interface TransactionService {

    TransactionDTO CreateTransaction(TransactionDTO transactionRequest);
    PagedResultsDTO<Long> GetTransactionIDsByType(TransactionType transactionType, int offset, int limit);
}
