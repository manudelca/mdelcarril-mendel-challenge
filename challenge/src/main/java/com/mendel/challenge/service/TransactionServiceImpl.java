package com.mendel.challenge.service;

import com.mendel.challenge.dto.service.TransactionDTO;
import com.mendel.challenge.dto.service.exception.ParentTransactionIdEqualsTransactionIdException;
import com.mendel.challenge.dto.service.exception.ParentTransactionNotFoundException;
import com.mendel.challenge.model.Transaction;
import com.mendel.challenge.model.enums.TransactionType;
import com.mendel.challenge.repository.TransactionRepository;
import com.mendel.challenge.repository.TransactionRepositoryImpl;
import com.mendel.challenge.util.PagedResultsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionDTO CreateTransaction(TransactionDTO transactionRequest) {
        if (Objects.equals(transactionRequest.getId(), transactionRequest.getParentId())) {
            throw new ParentTransactionIdEqualsTransactionIdException(transactionRequest.getId(), transactionRequest.getParentId(), null);
        }
        if (transactionRequest.getParentId() != null) {
            Transaction parentTransaction = this.transactionRepository.GetTransaction(transactionRequest.getParentId());
            if (parentTransaction == null) {
                throw new ParentTransactionNotFoundException(transactionRequest.getParentId(), null);
            }
        }

        Transaction transaction = new Transaction(transactionRequest.getId(),
                transactionRequest.getAmount(),
                transactionRequest.getType(),
                transactionRequest.getParentId());
        Transaction saveResp = this.transactionRepository.SaveTransaction(transaction);
        return new TransactionDTO(saveResp.getId(),
                saveResp.getAmount(),
                saveResp.getType(),
                saveResp.getParentId());
    }

    @Override
    public PagedResultsDTO<Long> GetTransactionIDsByType(TransactionType transactionType, int offset, int limit) {
        return this.transactionRepository.GetTransactionIDsByType(transactionType, offset, limit);
    }
}
