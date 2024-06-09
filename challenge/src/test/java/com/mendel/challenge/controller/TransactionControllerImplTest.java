package com.mendel.challenge.controller;

import com.mendel.challenge.dto.controller.CreateTransactionRequestDTO;
import com.mendel.challenge.dto.controller.CreateTransactionResponseDTO;
import com.mendel.challenge.dto.service.TransactionDTO;
import com.mendel.challenge.model.enums.TransactionType;
import com.mendel.challenge.service.TransactionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TransactionControllerImplTest {

    private AutoCloseable closeable;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;


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
        CreateTransactionRequestDTO transactionRequest = new CreateTransactionRequestDTO(100.0, "CARS", null);
        TransactionDTO transactionDTO = new TransactionDTO(1L, 100.0, TransactionType.CARS, null);
        Mockito.when(transactionService.CreateTransaction(Mockito.eq(transactionDTO))).thenReturn(transactionDTO);

        // When
        CreateTransactionResponseDTO result = transactionController.CreateTransaction(1L, transactionRequest);

        // Then
        assertEquals(1L, result.getId());
        assertEquals(100.0, result.getAmount());
        assertEquals(TransactionType.CARS, result.getType());
        assertNull(result.getParentId());

    }

    @Test
    void testCreateTransactionSuccessWithParentID() {
        // Given
        CreateTransactionRequestDTO transactionRequest = new CreateTransactionRequestDTO(100.0, "CARS", 2L);
        TransactionDTO transactionDTO = new TransactionDTO(1L, 100.0, TransactionType.CARS, 2L);
        Mockito.when(transactionService.CreateTransaction(Mockito.eq(transactionDTO))).thenReturn(transactionDTO);

        // When
        CreateTransactionResponseDTO result = transactionController.CreateTransaction(1L, transactionRequest);

        // Then
        assertEquals(1L, result.getId());
        assertEquals(100.0, result.getAmount());
        assertEquals(TransactionType.CARS, result.getType());
        assertEquals(2L, result.getParentId());
    }


}
