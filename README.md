# 💳 Backoffice Payment Routing Platform

This project implements a secure and resilient message routing system for the Payment Department of a bank.  
It was designed to demonstrate strong software engineering skills in the context of high-volume **IBM MQ**-based message processing with full web interface and service orchestration.

---

## 🚀 Functional Objectives

- Read messages from an **IBM MQ Series** queue.
- Persist messages in a **relational database**.
- Display messages in a modern **Angular UI** (table format).
- Show message **details in a modal window**.
- Provide **full CRUD APIs** for MQ partners.
- UI navigation with:
  - `Messages`
  - `Partners`

---

## 🧱 System Architecture

![image](https://github.com/user-attachments/assets/62c4bb70-6769-4cd4-89bf-22eca63f8de4)


---

## ⚙️ Technology Stack

| Component        | Technology                          |
|------------------|--------------------------------------|
| Backend           | Java 17+, Spring Boot 3.2.4, Maven   |
| Frontend          | Angular 19, Angular Material         |
| Monorepo          | Nx                                   |
| Messaging Queue   | IBM MQ Series                        |
| Database          | H2 (file persistence)                |
| API Docs          | Swagger OpenAPI                      |
| Deployment        | Docker & Docker Compose              |

---

## 🏁 Prerequisites

- [Docker](https://www.docker.com/) installed and started
```bash
docker info
```
- [Node.js](https://nodejs.org/en/) >= 18.x
```bash
node --version
```
- [npm](https://www.npmjs.com/) >= 9.x
```bash
npm --version
```
---

## 🛠️ How to Run


### 1. Clone the repository
```bash
git clone https://github.com/sebastienbruno/backoffice-payment-routing-platform.git
cd backoffice-payment-routing-platform
```

### 2. Install frontend dependencies
```bash
npm install
```

### 3. Build Docker images
```bash
npx nx run-many -t docker-build
```

### 4. Start the full stack
```bash
docker-compose up
```

---

## 🌐 Access Points

| Interface                   | URL                                                      |
|-----------------------------|-----------------------------------------------------------|
| Angular Frontend UI         | [http://localhost:4200](http://localhost:4200)            |
| Message API Swagger         | [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html) |
| Partner API Swagger         | [http://localhost:8084/swagger-ui/index.html](http://localhost:8084/swagger-ui/index.html) |

---

## 📚 Service Documentation

- Angular Frontend: [`apps/frontend-app/README.md`](apps/frontend-app/README.md)
- Partner Microservice: [`apps/partner-api-service/README.md`](apps/partner-api-service/README.md)
- Message Microservice: [`apps/message-api-service/README.md`](apps/message-api-service/README.md)

---

## ✅ Completed Features

### ✔️ Backend
- Reads messages from IBM MQ
- Persists them into H2 (file-based)
- Exposes REST APIs with full Swagger documentation
- Partner management: full CRUD with validations

### ✔️ Frontend
- Message table view
- Message detail modal
- Partner list and creation form
- Sidebar navigation

---

## 🔮 Roadmap (Future Work)

- [ ] Frontend Global HTTP error handler (`ErrorHandler`)
- [ ] Add a receiveAt timestamp field to messages to track when they were received from the MQ queue.
- [ ] HTTP action notifications via `Snackbar` (Create, Update, Delete)
- [ ] Frontend unit tests (httpResource is not yet supported in Angular 19)
- [ ] Add an **API Gateway** to centralize backend access and frontend configuration
- [ ] Monitoring integration (Micrometer / Prometheus)
- [ ] Retry / dead-letter queue handling for MQ delivery
- [ ] To improve scalability, the message listening and persistence logic could also be decoupled and moved into a dedicated microservice
- [ ] Extract and share common logic across microservices (e.g., global error handling, WebMvc configuration) using a shared library or module.

---

## 🧪 Testing Strategy

- **Backend**: Unit tests using **JUnit 5** and **MockMvc**
- **Frontend**: Planned (see roadmap)

---

## 🧼 Best Practices

- Fully **SOLID-compliant** backend design
- Modular, maintainable, and scalable architecture
- Clean and consistent code
- Clear API contracts via DTOs and validation
- Swagger documentation for all REST endpoints

---

## 🗂️ Repository Structure

```
.
├── apps/
│   ├── frontend-app/          # Angular 19 frontend
│   ├── partner-api-service/   # Spring Boot partner microservice
│   └── message-api-service/   # Spring Boot message microservice
├── docker-compose.yml
├── nx.json
├── package.json
└── pom.xml
```

---

## 📬 Contact

**Sébastien Bruno**  
📧 sebastien.bruno@gmail.com  
🔗 [github.com/sebastienbruno](https://github.com/sebastienbruno)
