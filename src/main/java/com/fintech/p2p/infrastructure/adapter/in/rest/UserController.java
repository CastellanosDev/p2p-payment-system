package com.fintech.p2p.infrastructure.adapter.in.rest;

import com.fintech.p2p.domain.model.User;
import com.fintech.p2p.domain.port.in.CreateUserUseCase;
import com.fintech.p2p.domain.port.in.GetBalanceUseCase;
import com.fintech.p2p.infrastructure.adapter.in.rest.dto.BalanceResponse;
import com.fintech.p2p.infrastructure.adapter.in.rest.dto.CreateUserRequest;
import com.fintech.p2p.infrastructure.adapter.in.rest.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * REST adapter (input) for user-related operations.
 *
 * Translates HTTP requests into use case calls and domain results
 * into HTTP responses. Contains no business logic.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetBalanceUseCase getBalanceUseCase;

    public UserController(CreateUserUseCase createUserUseCase,
                          GetBalanceUseCase getBalanceUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.getBalanceUseCase = getBalanceUseCase;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        // 1. Delegate to the use case, translating the DTO into its parameters
        User user = createUserUseCase.createUser(
                request.name(),
                request.email(),
                request.initialBalance()
        );

        // 2. Translate the domain result into a response DTO and return 201 Created
        UserResponse response = UserResponse.fromDomain(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable Long id) {
        // Delegate to the use case
        BigDecimal balance = getBalanceUseCase.getBalance(id);

        // Wrap the result in a response DTO and return 200 OK
        BalanceResponse response = new BalanceResponse(id, balance);
        return ResponseEntity.ok(response);
    }
}