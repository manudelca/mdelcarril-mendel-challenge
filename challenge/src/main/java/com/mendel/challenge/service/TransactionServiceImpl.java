package com.mendel.challenge.service;

import com.mendel.challenge.dto.controller.UpdateTransactionSumRequestDTO;
import com.mendel.challenge.dto.service.TransactionDTO;
import com.mendel.challenge.dto.service.exception.ParentTransactionIdEqualsTransactionIdException;
import com.mendel.challenge.dto.service.exception.ParentTransactionNotFoundException;
import com.mendel.challenge.dto.service.exception.TransactionNotFoundException;
import com.mendel.challenge.model.Transaction;
import com.mendel.challenge.model.enums.TransactionType;
import com.mendel.challenge.repository.TransactionQueue;
import com.mendel.challenge.repository.TransactionRepository;
import com.mendel.challenge.util.PagedResultsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionQueue transactionQueue;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionQueue transactionQueue) {
        this.transactionRepository = transactionRepository;
        this.transactionQueue = transactionQueue;
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

        Transaction originalTransaction = this.transactionRepository.GetTransaction(transactionRequest.getId());
        Transaction saveResp = this.transactionRepository.SaveTransaction(transactionRequest.buildTransactionFromDTO());

        if (originalTransaction != null && originalTransaction.getParentId() != null) {
            UpdateTransactionSumRequestDTO updateOriginal = new UpdateTransactionSumRequestDTO(
                    originalTransaction.getParentId(),
                    -originalTransaction.getAmount()
            );
            this.transactionQueue.PublishUpdateTransaction(updateOriginal);
        }
        if (saveResp.getParentId() != null) {
            UpdateTransactionSumRequestDTO updateUpdatedTransaction = new UpdateTransactionSumRequestDTO(
                    saveResp.getParentId(),
                    saveResp.getAmount()
            );
            this.transactionQueue.PublishUpdateTransaction(updateUpdatedTransaction);
        }
        return new TransactionDTO(saveResp);
    }

    @Override
    public PagedResultsDTO<Long> GetTransactionIDsByType(TransactionType transactionType, int offset, int limit) {
        return this.transactionRepository.GetTransactionIDsByType(transactionType, offset, limit);
    }

    @Override
    public void UpdateTransactionSum(Long id, Double sumDiff) {
        Transaction transaction = this.transactionRepository.GetTransaction(id);
        if (transaction == null) {
            return;
        }
        this.transactionRepository.AddTransactionSum(id, sumDiff);
        if (transaction.getParentId() != null) {
            UpdateTransactionSumRequestDTO request = new UpdateTransactionSumRequestDTO(
                    transaction.getParentId(),
                    sumDiff
            );
            this.transactionQueue.PublishUpdateTransaction(request);
        }
    }

    @Override
    public Double GetTransactionSum(Long id) {
        Transaction transaction = this.transactionRepository.GetTransaction(id);
        if (transaction == null) {
            throw new TransactionNotFoundException(id, null);
        }
        return this.transactionRepository.GetTransactionSum(id);
    }
}
