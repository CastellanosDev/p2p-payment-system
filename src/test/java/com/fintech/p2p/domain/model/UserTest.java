package com.fintech.p2p.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void createUser_withValidData_succeeds() {
        Account account = new Account(1L, new BigDecimal("100"));

        User user = new User(1L, "Ignacio", "ignacio@mail.com", account);

        assertEquals("Ignacio", user.getName());
        assertEquals("ignacio@mail.com", user.getEmail());
        assertEquals(account, user.getAccount());
    }

    @Test
    void createUser_withEmptyName_throwsException() {
        Account account = new Account(1L, new BigDecimal("100"));

        assertThrows(IllegalArgumentException.class,
                () -> new User(1L, "", "ignacio@mail.com", account));
    }

    @Test
    void createUser_withBlankEmail_throwsException() {
        Account account = new Account(1L, new BigDecimal("100"));

        assertThrows(IllegalArgumentException.class,
                () -> new User(1L, "Ignacio", "   ", account));
    }
}