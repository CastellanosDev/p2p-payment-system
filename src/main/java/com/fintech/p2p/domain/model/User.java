package com.fintech.p2p.domain.model;

/**
 * Represents a user of the payment system.
 *
 * A user has identity data (name, email) and an associated account
 * that holds their balance.
 */
public class User {

    private Long id;
    private String name;
    private String email;
    private Account account;

    public User(Long id, String name, String email, Account account) {
        validateName(name);
        validateEmail(email);
        this.id = id;
        this.name = name;
        this.email = email;
        this.account = account;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be empty");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email must not be empty");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Account getAccount() {
        return account;
    }
}