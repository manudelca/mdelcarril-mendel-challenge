package com.mendel.challenge.repository;

import com.mendel.challenge.dto.controller.UpdateTransactionSumRequestDTO;

public interface TransactionQueue {
    void PublishUpdateTransaction(UpdateTransactionSumRequestDTO update);
}
