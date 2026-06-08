package com.fintech.p2p.application.service;

import com.fintech.p2p.domain.exception.UserNotFoundException;
import com.fintech.p2p.domain.model.Account;
import com.fintech.p2p.domain.model.User;
import com.fintech.p2p.domain.port.out.UserRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GetBalanceServiceTest {

    @Test
    void getBalance_existingUser_returnsBalance() {
        // GIVEN: a repository that returns a user with balance 250
        UserRepository userRepository = mock(UserRepository.class);
        Account account = new Account(1L, new BigDecimal("250"));
        User user = new User(1L, "Ignacio", "ignacio@mail.com", account);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        GetBalanceService service = new GetBalanceService(userRepository);

        // WHEN: we ask for the balance
        BigDecimal balance = service.getBalance(1L);

        // THEN: we get 250
        assertEquals(new BigDecimal("250"), balance);
    }

    @Test
    void getBalance_userNotFound_throwsException() {
        // GIVEN: a repository that finds no user
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        GetBalanceService service = new GetBalanceService(userRepository);

        // WHEN / THEN: asking for a missing user throws
        assertThrows(UserNotFoundException.class,
                () -> service.getBalance(99L));
    }
}