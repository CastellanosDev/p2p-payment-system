package com.fintech.p2p.domain.model;

import com.fintech.p2p.domain.exception.InvalidAmountException;
import com.fintech.p2p.domain.exception.SameUserTransferException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void createTransaction_startsAsPending() {
        Transaction tx = new Transaction(1L, 2L, new BigDecimal("50"));

        assertEquals(TransactionStatus.PENDING, tx.getStatus());
    }

    @Test
    void markCompleted_changesStatusToCompleted() {
        Transaction tx = new Transaction(1L, 2L, new BigDecimal("50"));

        tx.markCompleted();

        assertEquals(TransactionStatus.COMPLETED, tx.getStatus());
    }

    @Test
    void markFailed_changesStatusToFailed() {
        Transaction tx = new Transaction(1L, 2L, new BigDecimal("50"));

        tx.markFailed();

        assertEquals(TransactionStatus.FAILED, tx.getStatus());
    }

    @Test
    void createTransaction_sameSenderAndReceiver_throwsException() {
        assertThrows(SameUserTransferException.class,
                () -> new Transaction(1L, 1L, new BigDecimal("50")));
    }

    @Test
    void createTransaction_negativeAmount_throwsException() {
        assertThrows(InvalidAmountException.class,
                () -> new Transaction(1L, 2L, new BigDecimal("-10")));
    }
}