# 🚀 p2p-payment-system

Event-driven **P2P payment backend** built with **Spring Boot, PostgreSQL, Redis and Apache Kafka**.

This project simulates a real fintech system where users transfer money between accounts while notifications, auditing and reporting are processed **asynchronously** using Kafka.

Designed with:

* clean architecture
* async messaging
* resilience
* Dockerized infrastructure

---

# 🧠 Why Kafka?

Without events → everything runs sequentially and failures propagate.

With Kafka → the transfer is completed first, and side effects run independently.

## ❌ Without Kafka

```
Transfer
├─ update balances
├─ write audit
├─ send notification
└─ update reports
```

If notifications fail → whole request may fail.

---

## ✅ With Kafka (event-driven)

```
Transfer completed
        ↓
Kafka Topic (transfer.completed)
        ↓
Audit | Notifications | Reports
```

Each consumer works independently.

**Benefits**

* resilience
* decoupling
* retry capability
* scalability

If notifications fail → transfer is still successful.

---

# ⚡ Quick Start

## Requirements

* Docker Desktop
* Java 21+

## Start everything

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

# 🐳 Infrastructure

Everything runs inside containers:

* postgres
* redis
* zookeeper
* kafka
* spring app

No local installation required.

---

# 🔥 Kafka Integration

## Topics

```
transfer.completed
transfer.failed
```

## Flow

```
TransactionService (Producer)
        ↓
Kafka
        ↓
NotificationConsumer
AuditConsumer
ReportingConsumer
```

## Dependency

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-kafka</artifactId>
</dependency>
```

---

# 🧪 Run tests

```bash
./mvnw test
```

---

# 🎯 Purpose

This project demonstrates:

* event-driven architecture
* async processing with Kafka
* real-world fintech transaction flows
* clean Spring Boot backend design
* Dockerized local environment

Ideal for backend / fintech interviews and portfolio.

---

# 👨‍💻 Author

Ignacio Castellanos
Senior Backend Engineer | Java & Spring Boot | Scalable Systems | Microservices

---
