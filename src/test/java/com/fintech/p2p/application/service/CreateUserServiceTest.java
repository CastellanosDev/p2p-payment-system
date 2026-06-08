package com.fintech.p2p.application.service;

import com.fintech.p2p.domain.model.User;
import com.fintech.p2p.domain.port.out.UserRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CreateUserServiceTest {

    @Test
    void createUser_buildsUserAndSavesIt() {
        // GIVEN: a fake repository (mock) that returns whatever user it receives
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CreateUserService service = new CreateUserService(userRepository);

        // WHEN: we create a user
        User result = service.createUser("Ignacio", "ignacio@mail.com", new BigDecimal("100"));

        // THEN: the returned user has the expected data
        assertEquals("Ignacio", result.getName());
        assertEquals("ignacio@mail.com", result.getEmail());
        assertEquals(new BigDecimal("100"), result.getAccount().getBalance());

        // AND: the repository's save() was actually called once
        verify(userRepository, times(1)).save(any(User.class));
    }
}