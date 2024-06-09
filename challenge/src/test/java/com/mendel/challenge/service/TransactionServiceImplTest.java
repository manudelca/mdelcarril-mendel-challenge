package com.mendel.challenge.service;

import com.mendel.challenge.dto.service.TransactionDTO;
import com.mendel.challenge.dto.service.exception.ParentTransactionIdEqualsTransactionIdException;
import com.mendel.challenge.dto.service.exception.ParentTransactionNotFoundException;
import com.mendel.challenge.model.Transaction;
import com.mendel.challenge.model.enums.TransactionType;
import com.mendel.challenge.repository.TransactionRepository;
import com.mendel.challenge.util.PagedResultsDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionServiceImplTest {

    private AutoCloseable closeable;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;


    @BeforeEach
    void init() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @Test
    void testCreateTransactionSuccess() {
        // Given

        TransactionDTO transactionRequest = new TransactionDTO(1L, 100.0, TransactionType.CARS, null);
        Mockito.when(transactionRepository.SaveTransaction(Mockito.any())).thenReturn(new Transaction(1L, 100.0, TransactionType.CARS, null));

        // When
        TransactionDTO result = transactionService.CreateTransaction(transactionRequest);

        // Then
        assertEquals(transactionRequest, result);
    }

    @Test
    void testCreateTransactionWithParentID() {
        // Given
        TransactionDTO transactionRequest = new TransactionDTO(2L, 100.0, TransactionType.CARS, 1L);
        Mockito.when(transactionRepository.GetTransaction(Mockito.eq(1L))).thenReturn(new Transaction(1L, 100.0, TransactionType.CARS, null));
        Transaction transactionRepoExpected = new Transaction(2L, 100.0, TransactionType.CARS, 1L);
        Transaction transactionRepoResponse = new Transaction(2L, 100.0, TransactionType.CARS, 1L);
        Mockito.when(transactionRepository.SaveTransaction(Mockito.eq(transactionRepoExpected))).thenReturn(transactionRepoResponse);

        // When
        TransactionDTO result = transactionService.CreateTransaction(transactionRequest);

        // Then
        assertEquals(transactionRequest, result);
    }

    @Test
    void testCreateTransactionThrowsParentTransactionIdEqualsTransactionIdException() {
        // Given
        TransactionDTO transactionRequest = new TransactionDTO(1L, 100.0, TransactionType.CARS, 1L);

        // Then
        assertThrows(ParentTransactionIdEqualsTransactionIdException.class, () -> {
            // When
            transactionService.CreateTransaction(transactionRequest);
        });
    }

    @Test
    void testCreateTransactionThrowsParentTransactionNotFoundException() {
        // Given
        TransactionDTO transactionRequest = new TransactionDTO(1L, 100.0, TransactionType.CARS, 2L);
        Mockito.when(transactionRepository.GetTransaction(2L)).thenReturn(null);

        // Then
        assertThrows(ParentTransactionNotFoundException.class, () -> {
            // When
            transactionService.CreateTransaction(transactionRequest);
        });
    }

    @Test
    void testGetTransactionByIDSuccess() {
        // Given
        PagedResultsDTO<Long> expectedResult = new PagedResultsDTO<>(0, 10, 3, Arrays.asList(1L, 2L, 3L));
        Mockito.when(transactionRepository.GetTransactionIDsByType(Mockito.eq(TransactionType.CARS), Mockito.eq(0), Mockito.eq(10))).thenReturn(expectedResult);

        // When
        PagedResultsDTO<Long> result = transactionService.GetTransactionIDsByType(TransactionType.CARS, 0, 10);

        // Then
        assertEquals(0, result.getPage().getOffset());
        assertEquals(3, result.getPage().getTotal());
        assertEquals(10, result.getPage().getLimit());
        assertEquals(1, result.getResult().get(0));
        assertEquals(2, result.getResult().get(1));
        assertEquals(3, result.getResult().get(2));
    }

}
