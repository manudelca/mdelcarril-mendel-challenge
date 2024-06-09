package com.mendel.challenge.repository;

import com.mendel.challenge.model.Transaction;
import com.mendel.challenge.model.enums.TransactionType;
import com.mendel.challenge.util.PagedResultsDTO;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private final Map<Long, Transaction> transactions;

    // El siguiente mapa vendría a representar a un índice en una base de datos.
    // Jamas guardaria un listado de IDs asociado a un Enum.
    private final Map<TransactionType, Set<Long>> typeIndex;


    public TransactionRepositoryImpl() {
        transactions = new HashMap<>();
        typeIndex = new HashMap<>();
    }

    @Override
    public Transaction GetTransaction(Long id) {
        return transactions.get(id);
    }

    @Override
    public Transaction SaveTransaction(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
        typeIndex.computeIfAbsent(transaction.getType(), _ -> new HashSet<>());
        typeIndex.get(transaction.getType()).add(transaction.getId());
        return transaction;
    }

    @Override
    public PagedResultsDTO<Long> GetTransactionIDsByType(TransactionType transactionType, int offset, int limit) {
        int total = typeIndex.getOrDefault(transactionType, new HashSet<>()).toArray().length;
        List<Long> values = typeIndex.getOrDefault(transactionType, new HashSet<>()).stream()
                .skip(offset)
                .limit(limit)
                .toList();
        return new PagedResultsDTO<Long>(offset, limit, total, values);
    }
}
