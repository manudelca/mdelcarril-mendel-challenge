package com.mendel.challenge.repository;

import com.mendel.challenge.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private final Map<Long, Transaction> transactions;
    private final Map<Long, Set<Long>> parentIndex;

    public TransactionRepositoryImpl() {
        transactions = new HashMap<>();
        parentIndex = new HashMap<>();
    }

    @Override
    public Transaction GetTransaction(Long id) {
        return transactions.get(id);
    }

    @Override
    public Transaction SaveTransaction(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
        if (transaction.getParentId() != null) {
            parentIndex.computeIfAbsent(transaction.getParentId(), _ -> new HashSet<>());
            parentIndex.get(transaction.getParentId()).add(transaction.getId());
        }
        return transaction;
    }
}
