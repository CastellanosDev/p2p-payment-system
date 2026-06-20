# 💸 P2P Payment System

> A peer-to-peer payment backend (Bizum / Venmo style) built with **Hexagonal Architecture**, **Spring Boot**, **PostgreSQL**, **Apache Kafka** and **Redis** — fully containerized with Docker.

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white" alt="Java 21"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.5-6DB33F?logo=springboot&logoColor=white" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/PostgreSQL-16-4169E1?logo=postgresql&logoColor=white" alt="PostgreSQL"/>
  <img src="https://img.shields.io/badge/Apache%20Kafka-3.9-231F20?logo=apachekafka&logoColor=white" alt="Kafka"/>
  <img src="https://img.shields.io/badge/Redis-7-DC382D?logo=redis&logoColor=white" alt="Redis"/>
  <img src="https://img.shields.io/badge/Docker-compose-2496ED?logo=docker&logoColor=white" alt="Docker"/>
</p>

---

## 📖 Overview

**P2P Payment System** is a backend service that lets users register, hold a balance and transfer money to each other — the core of any peer-to-peer payment app.

It was built as a portfolio project to demonstrate **clean, production-style backend architecture** rather than just "making it work". Every layer, dependency and technology choice is deliberate, and the codebase is designed to stay maintainable and testable as it grows.

### Key capabilities
- Register users with an initial balance
- Query a user's current balance (cached for performance)
- Transfer money between users **atomically** (all-or-nothing)
- Emit domain events on completed transfers (event-driven)
- Proper HTTP error semantics (404 / 422 / 400) via centralized handling

---

## 🏗️ Architecture: Hexagonal (Ports & Adapters)

The heart of this project is a **pure domain** that knows nothing about HTTP, SQL, Kafka or Redis. Technology lives at the edges, plugged in through *ports* (interfaces) and *adapters* (implementations).

```
                           ┌─────────────────────────────┐
        HTTP request  ───► │  IN ADAPTER (REST)           │
                           │  Controllers · DTOs · Errors │
                           └──────────────┬──────────────┘
                                          │ uses
                           ┌──────────────▼──────────────┐
                           │  INPUT PORT (Use Case)       │   ← what the app offers
                           └──────────────┬──────────────┘
                                          │ implemented by
                           ┌──────────────▼──────────────┐
                           │  APPLICATION (Service)       │   ← orchestrates the steps
                           └──────────────┬──────────────┘
                                          │ uses
                           ┌──────────────▼──────────────┐
                           │  DOMAIN (model + rules)      │   ← the pure core, no framework
                           │  Account · User · Transaction│
                           └──────────────┬──────────────┘
                                          │ needs
                           ┌──────────────▼──────────────┐
                           │  OUTPUT PORTS (interfaces)   │   ← what the domain needs
                           │  Repositories · EventPublisher│
                           └──────────────┬──────────────┘
                                          │ implemented by
              ┌───────────────────────────┼───────────────────────────┐
              ▼                            ▼                           ▼
   ┌────────────────────┐   ┌────────────────────────┐   ┌────────────────────┐
   │ OUT: persistence   │   │ OUT: messaging         │   │ OUT: cache         │
   │ JPA · PostgreSQL   │   │ Kafka producer         │   │ Redis              │
   └────────────────────┘   └────────────────────────┘   └────────────────────┘
                                          ▲
                           ┌──────────────┴──────────────┐
                           │  IN ADAPTER (messaging)      │
                           │  Kafka consumer (reacts)     │
                           └─────────────────────────────┘
```

**The golden rule:** dependencies always point *inward*.
`Infrastructure → Application → Domain`. The domain depends on nothing.

### Why this matters
- **Testable:** the business logic is tested with mocks, no database required.
- **Flexible:** swap PostgreSQL, Kafka or Redis without touching a single line of business logic.
- **Clear:** each class has one job. Business rules live in the domain; orchestration in services; technology at the adapters.

---

## 🧠 Design decisions (the *why*)

| Decision | Why |
|----------|-----|
| **Hexagonal architecture** | Keeps business logic independent from frameworks and infrastructure; makes the system testable and technology-agnostic. |
| **Rich domain model (no setters)** | `Account.withdraw()` enforces its own rules. Objects can never reach an invalid state from the outside. |
| **Separate domain model & JPA entity** | The domain stays pure; JPA constraints (no-args constructor, setters) never leak into business code. |
| **`BigDecimal` for money** | Avoids floating-point rounding errors (`0.1 + 0.2 ≠ 0.3`) — non-negotiable in finance. |
| **Domain exceptions, translated at the edge** | The domain says *what* went wrong (e.g. `InsufficientBalanceException`); a `@RestControllerAdvice` maps it to the right HTTP status. The core never knows about HTTP. |
| **`@Transactional` transfers** | A transfer is all-or-nothing: if anything fails mid-way, everything rolls back. Money never "disappears". |
| **Event-driven with Kafka** | The transfer service announces "transfer completed" without knowing who listens. New reactions (audit, stats…) can be added without touching it. |
| **Redis cache with eviction** | Frequent balance reads are served from memory; the cache is invalidated on every transfer to guarantee no stale balances. |
| **DTOs at the REST boundary** | The public API is decoupled from the internal domain — either can evolve without breaking the other. |

---

## 🛠️ Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot 3.5 (Web, Data JPA, Validation, Kafka, Data Redis)
- **Database:** PostgreSQL 16
- **Messaging:** Apache Kafka 3.9 (KRaft mode, no Zookeeper)
- **Cache:** Redis 7
- **Docs:** springdoc-openapi (Swagger UI)
- **Build:** Maven
- **Infra:** Docker Compose (PostgreSQL · Redis · Kafka · Kafka UI)

---

## 🚀 Getting Started

### Prerequisites
- Java 21
- Docker & Docker Compose

### 1. Start the infrastructure
```bash
docker compose up -d
```
This spins up PostgreSQL, Redis, Kafka and Kafka UI.

### 2. Run the application
```bash
./mvnw spring-boot:run
```
The app starts on **http://localhost:8080**.

### 3. Explore the API
- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **Kafka UI:** http://localhost:8085

---

## 📡 API Endpoints

| Method | Endpoint | Description | Success |
|--------|----------|-------------|---------|
| `POST` | `/api/users` | Create a user with an initial balance | `201 Created` |
| `GET`  | `/api/users/{id}/balance` | Get a user's balance (cached) | `200 OK` |
| `POST` | `/api/transactions` | Transfer money between two users | `201 Created` |

### Example: create a user
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{ "name": "Ana", "email": "ana@mail.com", "initialBalance": 100 }'
```

### Example: transfer money
```bash
curl -X POST http://localhost:8080/api/transactions \
  -H "Content-Type: application/json" \
  -d '{ "senderId": 1, "receiverId": 2, "amount": 30 }'
```

### Error semantics
| Situation | HTTP status |
|-----------|-------------|
| User not found | `404 Not Found` |
| Insufficient balance | `422 Unprocessable Entity` |
| Invalid amount / transfer to self | `400 Bad Request` |

---

## 🔄 How a transfer works (end to end)

1. `POST /api/transactions` hits the **REST adapter**, which validates the request DTO.
2. The controller calls the **TransferMoney use case**.
3. The **service** (`@Transactional`) loads both users, moves the money through the **domain** (`withdraw` / `deposit`, where the business rules live), marks the transaction completed and persists everything.
4. A **`TransactionCompletedEvent`** is published to **Kafka**.
5. A **Kafka consumer** reacts to the event (simulated notifications) — fully decoupled from the producer.
6. The next balance read is served from **Redis**; the cache was invalidated by the transfer so the value is always fresh.

---

## 🧪 Testing

Business logic is covered by **unit tests with Mockito** — no database or infrastructure needed, because the domain and application layers depend only on interfaces (ports).

```bash
./mvnw test
```

---

## 🗺️ Roadmap

Planned enhancements to take the project closer to a real production service:

- [ ] **Authentication & authorization** with JWT (secure all endpoints)
- [ ] **Transaction history** endpoint (per user)
- [ ] **Rate limiting** to protect against abuse
- [ ] **Integration tests** (full end-to-end with Testcontainers)
- [ ] **Observability**: metrics, structured logging, tracing
- [ ] **More granular cache eviction** (per-user instead of full flush)

---

## 📂 Project Structure

```
com.fintech.p2p/
├── domain/                  # Pure business core (no framework)
│   ├── model/               # Account, User, Transaction, TransactionStatus
│   ├── exception/           # Business exceptions
│   ├── event/               # Domain events
│   └── port/
│       ├── in/              # Use cases (input ports)
│       └── out/             # Repositories, EventPublisher (output ports)
├── application/
│   └── service/             # Use case implementations (orchestration)
└── infrastructure/
    ├── adapter/
    │   ├── in/
    │   │   ├── rest/         # Controllers, DTOs, error handling
    │   │   └── messaging/    # Kafka consumer
    │   └── out/
    │       ├── persistence/  # JPA entities, repositories, mappers, adapters
    │       ├── messaging/    # Kafka producer
    │       └── cache/        # Redis
    └── config/               # Spring configuration
```

---

## 👤 Author

**Ignacio Castellanos** — [GitHub @CastellanosDev](https://github.com/CastellanosDev)

Built as a portfolio project to demonstrate clean backend architecture and modern Java/Spring practices.

---

<p align="center"><i>⭐ If you find this project interesting, feel free to star the repo.</i></p>