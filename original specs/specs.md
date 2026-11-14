# Original Project Specifications — CEBP / PCBE (2026/2027)

## 1. Course General Requirements (Distributed Software Application)

- **Topic & Specification:** Chosen by the student team (*Energy Communities*), with the explicit approval of the lab supervisor.
- **HTTP APIs & OpenAPI Documentation:** Components that provide remote synchronous services expose them through HTTP REST APIs. All HTTP APIs (except MCP) must be documented following **OpenAPI** standards.
- **Distinct Distributed Components:** The application consists of **at least 3 distinct components** designed by the team that communicate remotely (e.g., 2 server types and 1 client) with distinct architectural designs and roles within the system.
- **Mobile Application & Geolocation:** Includes at most one mobile application running on phones or tablets, providing users (and administrators) with a lightweight mobile interface. The geographical location of the mobile component is used to optimize distributed resource management (e.g., detecting when the user is approaching home with an electric vehicle to reserve battery energy instead of selling it to the grid).
- **Event Streaming / Message Broker:** Uses advanced asynchronous communication technologies such as **Apache Kafka** or **RabbitMQ** to decouple high-frequency sensor telemetry from downstream consumers.
- **Single Source of Truth (SSoT):** Only **one** service (*Core Energy Service*) writes to the primary relational database. Other backend services (*Telemetry & Optimization Service*) build their own read views/projections from events and message queues without writing directly to the primary database.
- **Containerization:** The system deployment uses **Docker** and `docker-compose`.
- **Agentic AI & MCP Integration:**
  - Must provide a remotely accessible **MCP (Model Context Protocol) Server** with **at least 3 tools** allowing agentic interaction over the system: at least one tool that reads data and at least one tool that proposes a state change.
  - Must integrate an **autonomous agentic component** that uses the MCP tools to accomplish a concrete scheduled task (not a generic chatbot).

---

## 2. Project Theme Specification: *Energy Communities*

### 2.1 System Components

1. **Core Energy Service (Java Server — Member 1: Bianca Grovu)**
   - Acts as the **Single Source of Truth** for persistent system state.
   - Manages users (regular homeowners and administrators), household hardware configurations (battery capacity in kWh, solar panel arrays, geographical location), and battery schedule proposals.
   - Exposes OpenAPI-documented HTTP REST endpoints.
   - Is the **only** component authorized to write to the primary relational database (e.g., PostgreSQL).

2. **Telemetry & Optimization Service (Java Server — Member 2: Vlad Iordăchescu)**
   - Builds its own real-time read projection of the energy community by consuming events from the message broker (**Kafka / RabbitMQ**), without writing to the primary relational database.
   - Simulates and aggregates household sensor streams (instantaneous solar production, household power demand, battery state of charge, and dynamic grid price fluctuations).
   - Stores fast-access aggregated metrics and 24-hour solar/price forecasts in a secondary data store (e.g., Redis / in-memory time-series view) and exposes them via OpenAPI-documented HTTP endpoints.

3. **MCP Server & Agentic AI Optimizer (Java Server — Member 3: Luca-Adrian Mihuț)**
   - Exposes a remotely accessible **Model Context Protocol (MCP) Server** providing 3 standardized tools:
     - **MCP Tool 1 (Read):** `get_house_status(user_id)` — retrieves the current battery level (%), battery capacity (kWh), instantaneous household consumption (kW), solar production (kW), and EV proximity status.
     - **MCP Tool 2 (Read):** `get_forecasts(location)` — retrieves the 24-hour solar radiation forecast ($\text{W/m}^2$) and dynamic electricity prices (EUR/kWh) for the household's coordinates.
     - **MCP Tool 3 (Propose Change):** `propose_battery_schedule(user_id, action_plan)` — generates a `PENDING` battery optimization proposal in the system (e.g., *"Sell 40% of battery tonight at 19:00"*), visible in the mobile application.
   - **Scheduled Agentic Task:** Every evening, a scheduled job triggers the AI Optimization Agent, which invokes the MCP read tools, correlates solar weather forecasts with dynamic grid price peaks using an LLM (free tier), and invokes `propose_battery_schedule` to submit the most profitable energy plan for the next day.

4. **Mobile Client Application & Geolocation (Android / Kotlin — Member 4: Tatiana Lazăr)**
   - Provides the mobile interface for both regular users (`USER`) and community administrators (`ADMIN`).
   - Displays live household energy status, 24-hour forecasts, and AI-generated battery schedule proposals, allowing the homeowner to **Accept** or **Reject** pending plans.
   - Uses **device geolocation** to detect when the homeowner is approaching home (`APPROACHING_HOME`) with an electric vehicle (EV), notifying the backend to halt outbound battery energy sales and preserve charge for EV charging.
