package com.fintech.p2p.domain.port.in;

import com.fintech.p2p.domain.exception.InsufficientBalanceException;
import com.fintech.p2p.domain.exception.UserNotFoundException;
import com.fintech.p2p.domain.model.Transaction;

import java.math.BigDecimal;

/**
 * Input port for transferring money between two users.
 */
public interface TransferMoneyUseCase {

    /**
     * Transfers an amount from the sender to the receiver.
     * @return the completed transaction.
     * @throws UserNotFoundException        if sender or receiver does not exist.
     * @throws InsufficientBalanceException if the sender lacks enough balance.
     */
    Transaction transfer(Long senderId, Long receiverId, BigDecimal amount);
}