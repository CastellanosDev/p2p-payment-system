package com.fintech.p2p.domain.model;

import com.fintech.p2p.domain.exception.InvalidAmountException;
import com.fintech.p2p.domain.exception.SameUserTransferException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a money transfer between two users.
 *
 * A transaction always starts in PENDING state and is later marked
 * as COMPLETED or FAILED. The sender and receiver must be different,
 * and the amount must be positive.
 */
public class Transaction {

    private Long id;
    private Long senderId;
    private Long receiverId;
    private BigDecimal amount;
    private TransactionStatus status;
    private LocalDateTime createdAt;

    public Transaction(Long senderId, Long receiverId, BigDecimal amount) {
        validateDifferentUsers(senderId, receiverId);
        validateAmount(amount);
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.status = TransactionStatus.PENDING;   // every transaction starts as pending
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Marks this transaction as successfully completed.
     */
    public void markCompleted() {
        this.status = TransactionStatus.COMPLETED;
    }

    /**
     * Marks this transaction as failed.
     */
    public void markFailed() {
        this.status = TransactionStatus.FAILED;
    }

    private void validateDifferentUsers(Long senderId, Long receiverId) {
        if (senderId != null && senderId.equals(receiverId)) {
            throw new SameUserTransferException("Sender and receiver must be different");
        }
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero");
        }
    }

    public Long getId() {
        return id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}