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

    // Almacenamiento aparte para almacenar la suma total de las transacciones
    private final Map<Long, Double> transacitonsSum;

    public TransactionRepositoryImpl() {
        transactions = new HashMap<>();
        typeIndex = new HashMap<>();
        transacitonsSum = new HashMap<>();
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
        this.transacitonsSum.put(transaction.getId(), transaction.getAmount());
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

    @Override
    public void AddTransactionSum(Long id, Double amount) {
        transacitonsSum.computeIfAbsent(id, _ -> 0.0);
        transacitonsSum.compute(id, (k, currentSum) -> currentSum + amount);
    }

    @Override
    public Double GetTransactionSum(Long id) {
        return transacitonsSum.get(id);
    }
}
