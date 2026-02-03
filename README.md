# 🚀 p2p-payment-system

Event-driven **P2P payment backend** built with **Spring Boot, PostgreSQL, Redis and Apache Kafka**.

This project simulates a real fintech system where users transfer money between accounts while notifications, auditing and reporting are processed **asynchronously** using Kafka.

Designed with:

* clean architecture
* async messaging
* resilience
* Dockerized infrastructure

---
## 🎯 Project Goals

- Simulate a real-world **fintech P2P payment flow**
- Apply **Hexagonal Architecture (Ports & Adapters)**
- Implement **event-driven communication** with Kafka
- Ensure **transactional consistency** (ACID)
- Handle **idempotency and retries** with Redis
- Run everything locally using **Docker Compose**
- Serve as a **production-grade backend portfolio project**

---

# 🧠 Key Principles

- Domain layer is **framework-independent**
- Business logic isolated from infrastructure
- Dependencies point **inwards**
- Easy to test and evolve
- 
---

## 🛠 Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring Data JPA
- Spring Kafka
- Spring Validation

### 🐳 Infrastructure
- PostgreSQL
- Redis
- Apache Kafka (KRaft mode)
- Docker & Docker Compose

### Tooling
- DBeaver (PostgreSQL UI)
- Kafka UI (Kafka monitoring)
- Swagger / OpenAPI

---

## Requirements

* Docker Desktop
* Java 21+

---

## 🎯  Start everything

```bash
docker compose up -d
```

Services:

```
PostgreSQL → localhost:5432
Redis      → localhost:6379
Kafka      → localhost:9092
API        → localhost:8080
```

Swagger:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Stop

```bash
docker compose down
```
---

# 🧪 Run tests

```bash
./mvnw test
```
---

# 👨‍💻 Author

Ignacio Castellanos
Senior Backend Engineer | Java & Spring Boot | Scalable Systems | Microservices

---
