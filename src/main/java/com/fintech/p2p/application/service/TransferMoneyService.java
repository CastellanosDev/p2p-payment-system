package com.fintech.p2p.application.service;

import com.fintech.p2p.domain.event.TransactionCompletedEvent;
import com.fintech.p2p.domain.exception.UserNotFoundException;
import com.fintech.p2p.domain.model.Transaction;
import com.fintech.p2p.domain.model.User;
import com.fintech.p2p.domain.port.in.TransferMoneyUseCase;
import com.fintech.p2p.domain.port.out.EventPublisher;
import com.fintech.p2p.domain.port.out.TransactionRepository;
import com.fintech.p2p.domain.port.out.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Application service implementing the transfer-money use case.
 *
 * Orchestrates the full flow: load both users, move the money through
 * the domain, persist the changes and publish a domain event.
 * The whole operation is transactional: if anything fails, nothing is applied.
 */
@Service
public class TransferMoneyService implements TransferMoneyUseCase {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final EventPublisher eventPublisher;

    public TransferMoneyService(UserRepository userRepository,
                                TransactionRepository transactionRepository,
                                EventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    @CacheEvict(value = "balances", allEntries = true)
    public Transaction transfer(Long senderId, Long receiverId, BigDecimal amount) {
        // 1. Load both users (fail if either does not exist)
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new UserNotFoundException("Sender not found with id " + senderId));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new UserNotFoundException("Receiver not found with id " + receiverId));

        // 2. Create the transaction (validates amount and different users)
        Transaction transaction = new Transaction(senderId, receiverId, amount);

        // 3. Move the money through the domain (withdraw validates balance)
        sender.getAccount().withdraw(amount);
        receiver.getAccount().deposit(amount);

        // 4. Mark the transaction as completed
        transaction.markCompleted();

        // 5. Persist the changes: both users and the transaction
        userRepository.save(sender);
        userRepository.save(receiver);
        Transaction saved = transactionRepository.save(transaction);

        // 6. Publish the domain event
        eventPublisher.publishTransactionCompleted(new TransactionCompletedEvent(
                saved.getId(), senderId, receiverId, amount, LocalDateTime.now()
        ));

        return saved;
    }
}