package com.fintech.p2p.domain.model;

import com.fintech.p2p.domain.exception.InsufficientBalanceException;
import com.fintech.p2p.domain.exception.InvalidAmountException;

import java.math.BigDecimal;

/**
 * Represents a user's account holding a monetary balance.
 *
 * The balance can only be modified through deposit() and withdraw(),
 * which enforce the business rules. There is no public setter for the
 * balance, so the account can never be left in an invalid state from outside.
 */
public class Account {

    private Long id;
    private BigDecimal balance;

    public Account(Long id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    /**
     * Adds money to the account.
     * @throws InvalidAmountException if the amount is null, zero or negative.
     */
    public void deposit(BigDecimal amount) {
        validateAmount(amount);
        this.balance = this.balance.add(amount);
    }

    /**
     * Removes money from the account.
     * @throws InvalidAmountException      if the amount is null, zero or negative.
     * @throws InsufficientBalanceException if the balance is lower than the amount.
     */
    public void withdraw(BigDecimal amount) {
        validateAmount(amount);
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException(
                    "Cannot withdraw " + amount + ", current balance is " + balance
            );
        }
        this.balance = this.balance.subtract(amount);
    }

    /**
     * Shared validation: an amount must be present and strictly positive.
     */
    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero");
        }
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}