package com.mendel.challenge.repository;

import com.mendel.challenge.model.Transaction;

public interface TransactionRepository {

    Transaction GetTransaction(Long id);
    Transaction SaveTransaction(Transaction transaction);
}
