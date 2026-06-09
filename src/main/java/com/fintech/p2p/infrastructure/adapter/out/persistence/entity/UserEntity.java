package com.fintech.p2p.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * JPA entity mapping the "users" table.
 *
 * This is a persistence concern, kept separate from the domain model (User).
 * It has a no-args constructor and setters because JPA requires them.
 * The domain stays pure; this class adapts it to the database.
 */
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private BigDecimal balance;

    // JPA requires a no-args constructor
    public UserEntity() {
    }

    public UserEntity(Long id, String name, String email, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}