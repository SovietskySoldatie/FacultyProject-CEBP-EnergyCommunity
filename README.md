# FacultyProject-CEBP-EnergyCommunity

## About the Project & Course

This repository contains the laboratory project implementation for the following academic course:

- **Course:** Concurrent and Event-Based Programming (CEBP / PCBE — _Programare Concurentă și Bazată pe Evenimente_)
- **Academic Year:** 2026/2027 — Year 4, Semester 1
- **Institution:** Politehnica University of Timișoara (UPT) — Faculty of Automatics and Computers, Department of Computer Science and Information Technology
- **Project Topic:** _Energy Communities_
- **Objective:** Design and implementation of an event-driven distributed software system integrating REST OpenAPI services, asynchronous message streaming (Kafka/RabbitMQ), mobile geolocation, and Agentic AI via a remote Model Context Protocol (MCP) Server.

---

## 👥 Team Members & Responsibilities

The project is developed by a team of 4 students:

| Member | GitHub | Role & Component |
| :--- | :--- | :--- |
| **Bianca Grovu** | [@BiaG14](https://github.com/BiaG14) | **Member 1 — Core Backend & Data Architect:** `EnergyCommunity.core.database` & `core.api` (Single Source of Truth relational DB, users, houses, proposals persistence, OpenAPI REST endpoints) |
| **Vlad Iordăchescu** | [@SovietskySoldatie](https://github.com/SovietskySoldatie) | **Member 2 — Data Streaming & Telemetry:** `EnergyCommunity.messageBroker` & `core.telemetry` (Kafka/RabbitMQ event streaming, sensor simulation, Redis fast read projections, 24h forecasts) |
| **Luca-Adrian Mihuț** | [@lukettoOoO](https://github.com/lukettoOoO) | **Member 3 — Agentic AI, MCP Server & Docs:** `EnergyCommunity.core.mcpServer` (Remote MCP server, 3 MCP tools, scheduled LLM optimization agent, system documentation) |
| **Tatiana Lazăr** | [@per1cle](https://github.com/per1cle) | **Member 4 — Mobile App & Geolocation:** `EnergyCommunity.user.mobileApp` (Android Jetpack Compose client for `USER` & `ADMIN` roles, GPS EV proximity detection, proposal approval UI) |

---

## 📚 Documentation & Project Specifications

- **Original Project Specifications:** See [`original specs/specs.md`](original%20specs/specs.md)
- **Full Architecture, Specifications & Diagrams:** See [`reqs/reqs.md`](reqs/reqs.md) for the distributed system architecture and Mermaid sequence and state diagrams
- **MCP Server & Agentic AI Guide:** See [`docs/mcp-agent-guide.md`](docs/mcp-agent-guide.md)
- **Project Task Breakdown & Progress Tracking:** All tasks and deliverables are decoupled and maintained in a dedicated file:  
  👉 **[`TO-DO.md`](TO-DO.md)**
