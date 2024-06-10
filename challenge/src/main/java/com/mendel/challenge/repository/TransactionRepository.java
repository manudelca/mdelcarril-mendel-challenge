package com.mendel.challenge.repository;

import com.mendel.challenge.model.Transaction;
import com.mendel.challenge.model.enums.TransactionType;
import com.mendel.challenge.util.PagedResultsDTO;

public interface TransactionRepository {

    Transaction GetTransaction(Long id);
    Transaction SaveTransaction(Transaction transaction);
    PagedResultsDTO<Long> GetTransactionIDsByType(TransactionType transactionType, int offset, int limit);
    void AddTransactionSum(Long id, Double amount);
    Double GetTransactionSum(Long id);
}
