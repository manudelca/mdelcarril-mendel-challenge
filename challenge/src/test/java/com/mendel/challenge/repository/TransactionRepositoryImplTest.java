package com.mendel.challenge.repository;

import com.mendel.challenge.model.Transaction;
import com.mendel.challenge.model.enums.TransactionType;
import com.mendel.challenge.util.PagedResultsDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionRepositoryImplTest {

    private AutoCloseable closeable;

    @InjectMocks
    private TransactionRepositoryImpl transactionRepository;


    @BeforeEach
    void init() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @Test
    public void testGetTransactionDoesNotExist() {
        // When
        Transaction transaction = transactionRepository.GetTransaction(1L);

        // Then
        assertNull(transaction);
    }

    @Test
    public void testGetTransactionExists() {
        // Given
        Transaction transaction = new Transaction(
                1L,
                10.0,
                TransactionType.CARS,
                null);

        Transaction savedTransaction = transactionRepository.SaveTransaction(transaction);
        assertNotNull(savedTransaction);

        // When
        Transaction getTransactionResponse = transactionRepository.GetTransaction(1L);

        // Then
        assertEquals(transaction, getTransactionResponse);
    }

    @Test
    public void testSaveTransactionWithoutParent() {
        // Given
        Transaction transaction = new Transaction(
                1L,
                10.0,
                TransactionType.CARS,
                null);

        // When
        Transaction savedTransaction = transactionRepository.SaveTransaction(transaction);

        // Then
        assertEquals(transaction, savedTransaction);
        assertNull(savedTransaction.getParentId());
        assertEquals(transaction, transactionRepository.GetTransaction(1L));
    }


    @Test
    public void
    testSaveTransactionWithParent() {
        // Given
        Transaction parentTransaction = new Transaction(
                1L,
                10.0,
                TransactionType.CARS,
                null);
        Transaction parentSavedTransaction = transactionRepository.SaveTransaction(parentTransaction);
        assertNotNull(parentSavedTransaction);
        Transaction transaction = new Transaction(
                2L,
                10.0,
                TransactionType.CARS,
                parentTransaction.getId());


        // When
        Transaction savedTransaction = transactionRepository.SaveTransaction(transaction);

        // Then
        assertEquals(transaction, savedTransaction);
        assertNotNull(savedTransaction.getParentId());
        assertEquals(transaction, transactionRepository.GetTransaction(2L));
    }


    @Test
    void testGetTransactionByIDNoTransactions() {
        // When
        PagedResultsDTO<Long> result = transactionRepository.GetTransactionIDsByType(TransactionType.CARS, 0, 10);

        // Then
        assertEquals(0, result.getPage().getOffset());
        assertEquals(0, result.getPage().getTotal());
        assertEquals(10, result.getPage().getLimit());
        assertEquals(0, result.getResult().size());
    }

    @Test
    void testGetTransactionByIDWithTransactions() {
        // Given
        Transaction transaction = new Transaction(
                1L,
                10.0,
                TransactionType.CARS,
                null);

        Transaction savedTransaction = transactionRepository.SaveTransaction(transaction);
        assertNotNull(savedTransaction);

        // When
        PagedResultsDTO<Long> result = transactionRepository.GetTransactionIDsByType(TransactionType.CARS, 0, 10);

        // Then
        assertEquals(0, result.getPage().getOffset());
        assertEquals(1, result.getPage().getTotal());
        assertEquals(10, result.getPage().getLimit());
        assertEquals(1, result.getResult().size());
        assertEquals(1, result.getResult().getFirst());
    }

}
