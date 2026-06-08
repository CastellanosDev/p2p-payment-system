package com.fintech.p2p.domain.model;

import com.fintech.p2p.domain.exception.InsufficientBalanceException;
import com.fintech.p2p.domain.exception.InvalidAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private Account account;

    // Runs before EACH test: every test starts with a fresh account holding 100.
    @BeforeEach
    void setUp() {
        account = new Account(1L, new BigDecimal("100"));
    }

    @Test
    void deposit_increasesBalance() {
        account.deposit(new BigDecimal("50"));
        assertEquals(new BigDecimal("150"), account.getBalance());
    }

    @Test
    void withdraw_decreasesBalance() {
        account.withdraw(new BigDecimal("30"));
        assertEquals(new BigDecimal("70"), account.getBalance());
    }

    @Test
    void withdraw_moreThanBalance_throwsException() {
        assertThrows(InsufficientBalanceException.class,
                () -> account.withdraw(new BigDecimal("150")));
    }

    @Test
    void deposit_negativeAmount_throwsException() {
        assertThrows(InvalidAmountException.class,
                () -> account.deposit(new BigDecimal("-10")));
    }

    @Test
    void withdraw_zeroAmount_throwsException() {
        assertThrows(InvalidAmountException.class,
                () -> account.withdraw(BigDecimal.ZERO));
    }

    @Test
    void withdraw_entireBalance_leavesZero() {
        Account richAccount = new Account(2L, new BigDecimal("500"));

        richAccount.withdraw(new BigDecimal("500"));

        assertEquals(new BigDecimal("0"), richAccount.getBalance());
    }

    @Test
    void withdraw_fromEmptyAccount_throwsException() {
        Account emptyAccount = new Account(3L, BigDecimal.ZERO);

        assertThrows(InsufficientBalanceException.class,
                () -> emptyAccount.withdraw(new BigDecimal("10")));
    }
}