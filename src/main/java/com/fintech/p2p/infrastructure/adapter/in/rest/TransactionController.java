package com.fintech.p2p.infrastructure.adapter.in.rest;

import com.fintech.p2p.domain.model.Transaction;
import com.fintech.p2p.domain.port.in.TransferMoneyUseCase;
import com.fintech.p2p.infrastructure.adapter.in.rest.dto.TransactionResponse;
import com.fintech.p2p.infrastructure.adapter.in.rest.dto.TransferRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST adapter (input) for transaction operations.
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransferMoneyUseCase transferMoneyUseCase;

    public TransactionController(TransferMoneyUseCase transferMoneyUseCase) {
        this.transferMoneyUseCase = transferMoneyUseCase;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransferRequest request) {
        // Delegate to the use case
        Transaction transaction = transferMoneyUseCase.transfer(
                request.senderId(),
                request.receiverId(),
                request.amount()
        );

        // Translate to response DTO and return 201 Created
        TransactionResponse response = TransactionResponse.fromDomain(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}