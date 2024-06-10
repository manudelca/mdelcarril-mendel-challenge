package com.mendel.challenge.controller;

import com.mendel.challenge.dto.controller.CreateTransactionRequestDTO;
import com.mendel.challenge.dto.controller.CreateTransactionResponseDTO;
import com.mendel.challenge.dto.controller.GetTransactionSumResponseDTO;
import com.mendel.challenge.model.enums.TransactionType;
import com.mendel.challenge.service.TransactionService;
import com.mendel.challenge.dto.service.TransactionDTO;
import com.mendel.challenge.util.PagedResultsDTO;
import com.mendel.challenge.util.ValueOfEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PutMapping("/transactions/{transactionId}")
    public CreateTransactionResponseDTO CreateTransaction(@Valid @PathVariable("transactionId") Long transactionId, @Valid @RequestBody CreateTransactionRequestDTO transaction) {
        TransactionDTO transactionDTO = new TransactionDTO(transactionId,
                    transaction.getAmount(), TransactionType.valueOf(transaction.getType()), transaction.getParentId());
        TransactionDTO response = this.transactionService.CreateTransaction(transactionDTO);
        return new CreateTransactionResponseDTO(response.getId(),
                response.getAmount(),
                response.getType(),
                response.getParentId());
    }

    @GetMapping("/transactions/types/{type}")
    public PagedResultsDTO<Long> GetTransactionIDsByType(
                                    @Valid @NotNull @ValueOfEnum(enumClass = TransactionType.class) @PathVariable("type") String transactionType,
                                    @Valid @NotNull @Min(value = 0) @RequestParam int offset,
                                    @Valid @NotNull @Max(value = 50) @RequestParam int limit) {
        return this.transactionService.GetTransactionIDsByType(TransactionType.valueOf(transactionType.toUpperCase()), offset, limit);
    }

    @GetMapping("/transactions/sum/{transactionId}")
    public GetTransactionSumResponseDTO GetTransactionSum(@Valid @NotNull @PathVariable("transactionId") Long transactionId) {
        return new GetTransactionSumResponseDTO(this.transactionService.GetTransactionSum(transactionId));
    }
}
