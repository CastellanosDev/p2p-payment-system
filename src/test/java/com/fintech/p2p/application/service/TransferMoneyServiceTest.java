package com.fintech.p2p.application.service;

import com.fintech.p2p.domain.event.TransactionCompletedEvent;
import com.fintech.p2p.domain.exception.InsufficientBalanceException;
import com.fintech.p2p.domain.exception.UserNotFoundException;
import com.fintech.p2p.domain.model.Account;
import com.fintech.p2p.domain.model.Transaction;
import com.fintech.p2p.domain.model.TransactionStatus;
import com.fintech.p2p.domain.model.User;
import com.fintech.p2p.domain.port.out.EventPublisher;
import com.fintech.p2p.domain.port.out.TransactionRepository;
import com.fintech.p2p.domain.port.out.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TransferMoneyServiceTest {

    private UserRepository userRepository;
    private TransactionRepository transactionRepository;
    private EventPublisher eventPublisher;
    private TransferMoneyService service;

    @BeforeEach
    void setUp() {
        // Fresh mocks before each test
        userRepository = mock(UserRepository.class);
        transactionRepository = mock(TransactionRepository.class);
        eventPublisher = mock(EventPublisher.class);
        service = new TransferMoneyService(userRepository, transactionRepository, eventPublisher);

        // The transaction repository returns whatever transaction it receives
        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void transfer_validTransfer_movesMoneyAndCompletes() {
        // GIVEN: sender with 100, receiver with 50
        User sender = new User(1L, "Sender", "sender@mail.com", new Account(1L, new BigDecimal("100")));
        User receiver = new User(2L, "Receiver", "receiver@mail.com", new Account(2L, new BigDecimal("50")));
        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepository.findById(2L)).thenReturn(Optional.of(receiver));

        // WHEN: transfer 30 from sender to receiver
        Transaction result = service.transfer(1L, 2L, new BigDecimal("30"));

        // THEN: balances moved correctly
        assertEquals(new BigDecimal("70"), sender.getAccount().getBalance());
        assertEquals(new BigDecimal("80"), receiver.getAccount().getBalance());

        // AND: the transaction is completed
        assertEquals(TransactionStatus.COMPLETED, result.getStatus());

        // AND: both users were saved and the event was published once
        verify(userRepository, times(1)).save(sender);
        verify(userRepository, times(1)).save(receiver);
        verify(eventPublisher, times(1)).publishTransactionCompleted(any(TransactionCompletedEvent.class));
    }

    @Test
    void transfer_insufficientBalance_throwsAndDoesNotPublish() {
        // GIVEN: sender with only 10, receiver with 50
        User sender = new User(1L, "Sender", "sender@mail.com", new Account(1L, new BigDecimal("10")));
        User receiver = new User(2L, "Receiver", "receiver@mail.com", new Account(2L, new BigDecimal("50")));
        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepository.findById(2L)).thenReturn(Optional.of(receiver));

        // WHEN / THEN: transferring 100 fails because of insufficient balance
        assertThrows(InsufficientBalanceException.class,
                () -> service.transfer(1L, 2L, new BigDecimal("100")));

        // AND: no event was published, because the transfer never completed
        verify(eventPublisher, never()).publishTransactionCompleted(any(TransactionCompletedEvent.class));
    }

    @Test
    void transfer_senderNotFound_throwsException() {
        // GIVEN: sender does not exist
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // WHEN / THEN: it throws and never reaches the receiver lookup
        assertThrows(UserNotFoundException.class,
                () -> service.transfer(1L, 2L, new BigDecimal("30")));

        verify(eventPublisher, never()).publishTransactionCompleted(any(TransactionCompletedEvent.class));
    }
}