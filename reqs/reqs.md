# System Architecture, Requirements & Diagrams — Energy Community

> **Note:** This document is a living architectural specification. Interface contracts for **Member 3 (MCP Server & Agentic AI)** and **Member 4 (Mobile Client)** are finalized; backend REST/broker details for **Member 1 (Core Energy Service)** and **Member 2 (Telemetry & Streaming)** will be updated as their respective interface PRs are merged.

---

## 1. High-Level Distributed Architecture

The **Energy Community** platform is designed as an event-driven, distributed system composed of four distinct runtime components, a message broker, a primary relational store (Single Source of Truth), and a fast telemetry read store.

```mermaid
flowchart TB
    subgraph ClientLayer["Client Layer (Member 4)"]
        MobileApp["Android Mobile App (USER & ADMIN)<br/>Geolocation & Proposal Review"]
    end

    subgraph AgenticLayer["Agentic AI & MCP Layer (Member 3)"]
        Agent["Scheduled Energy Optimization Agent<br/>(Nightly Cron + On-Demand Trigger)"]
        LLM["External LLM Provider<br/>(Gemini / OpenAI-Compatible Free Tier)"]
        MCPServer["Remote MCP Server<br/>Tools: get_house_status, get_forecasts, propose_battery_schedule"]
    end

    subgraph BackendCore["Backend Services (Members 1 & 2)"]
        CoreAPI["Public API Boundary (core.api)<br/>OpenAPI REST Contracts"]
        CoreService["Core Energy Service (Member 1)<br/>Single Source of Truth Writer"]
        TelemetryService["Telemetry & Optimization Service (Member 2)<br/>Stream Processor & Forecast Engine"]
    end

    subgraph MessagingAndStorage["Messaging & Persistence Infrastructure"]
        Broker["Message Broker (Kafka / RabbitMQ)<br/>High-Frequency Sensor & Price Events"]
        PrimaryDB[("Primary Relational DB (PostgreSQL)<br/>Users, Houses, Assets, Proposals")]
        FastStore[("Secondary Read Store (Redis)<br/>Live Telemetry & 24h Forecasts")]
        Sensors["Simulated Smart Meters & Solar Sensors"]
    end

    MobileApp -->|"HTTP REST (OpenAPI)"| CoreAPI
    Sensors -->|"Publish Telemetry & Tariff Events"| Broker
    Broker -->|"Consume Event Stream"| TelemetryService
    TelemetryService -->|"Read/Write Fast Projections"| FastStore
    CoreAPI --> CoreService
    CoreAPI --> TelemetryService
    CoreService -->|"Sole Writer (SSoT)"| PrimaryDB

    Agent <-->|"Tool Definitions & Tool Calls"| LLM
    Agent <-->|"MCP Protocol (tools/list, tools/call)"| MCPServer
    MCPServer -->|"Read Status & Forecasts (Ports)"| TelemetryService
    MCPServer -->|"Submit PENDING Proposal (ProposalSubmissionPort)"| CoreService
```

---

## 2. Package Structure & Component Boundaries

| Package | Owner | Visibility | Architectural Role |
| :--- | :--- | :--- | :--- |
| `EnergyCommunity.core.api` | Co-owned (Backend) | `PUBLIC` | Shared service interfaces and DTOs exposed to external callers. |
| `EnergyCommunity.core.database` | Member 1 (Bianca) | `PRIVATE` | **Single Source of Truth** persistence layer for `User`, `House`, `Battery`, `SolarPanel`, and `BatteryProposal`. |
| `EnergyCommunity.messageBroker` | Member 2 (Vlad) | `TRANSPORT` | Asynchronous pub/sub event bus (Kafka/RabbitMQ) decoupling smart-meter sensors from downstream consumers. |
| `EnergyCommunity.core.telemetry` | Member 2 (Vlad) | `INTERNAL` | Consumes broker events, builds fast read views in Redis, and computes consumption averages and 24h forecasts. |
| `EnergyCommunity.core.mcpServer` | Member 3 (Luca) | `PROTOCOL` | Exposes the remote MCP Server (`McpServerContract`), the 3 MCP tools, and the scheduled AI optimization agent (`EnergyOptimizationAgentContract`). |
| `EnergyCommunity.user.mobileApp` | Member 4 (Tatiana) | `CLIENT` | Android application (Jetpack Compose) supporting `USER` and `ADMIN` roles, GPS proximity detection, and proposal approval. |
| `EnergyCommunity.deployment` | Shared (Vlad + Team) | `INFRA` | Dockerization, health checks, and service startup orchestration. |

---

## 3. Member 3 Specification: MCP Server & Agentic AI (`EnergyCommunity.core.mcpServer`)

### 3.1 Model Context Protocol (MCP) Tools

The MCP Server (`McpServerContract`) exposes three tools over remote transport:

1. **`get_house_status(user_id)` — Read Tool (`GetHouseStatusToolContract`)**
   - **Input:** `user_id` (`String`)
   - **Output (`HouseStatusReadResult`):** `houseId`, `location` (`latitude`, `longitude`, `regionName`), `batteryLevelPercent` (0–100%), `batteryCapacityKwh`, `currentConsumptionKw`, `currentSolarProductionKw`, `evApproachingHome` (`boolean`), and `updatedAt` (`Instant`).
   - **Data Source:** Queries the backend read projection via `HouseStatusQueryPort`.

2. **`get_forecasts(location)` — Read Tool (`GetForecastsToolContract`)**
   - **Input:** `location` (`GeoLocationInput`: `latitude`, `longitude`, `regionName`)
   - **Output (`ForecastReadResult`):** 24-hour chronological list of `ForecastPointData(time, solarRadiationWm2, pricePerKwh)`.
   - **Data Source:** Queries the Telemetry & Forecast service via `ForecastQueryPort`.

3. **`propose_battery_schedule(user_id, action_plan)` — Propose Change Tool (`ProposeBatteryScheduleToolContract`)**
   - **Input:** `user_id` (`String`) and `action_plan` (`BatteryActionPlan`: `houseId`, `actionType`, `batteryPercentage`, `executeAt`, `estimatedProfitEur`, `description`, `reasoning`).
   - **Output (`ProposalSubmissionResult`):** Registered `BatteryProposal` with initial status `ProposalStatus.PENDING`.
   - **Data Source:** Forwards the proposal via `ProposalSubmissionPort` so that only the Core Energy Service writes to the primary database.

### 3.2 Scheduled Agentic Workflow (`EnergyOptimizationAgentContract`)

Unlike a reactive chatbot, the `EnergyOptimizationAgentContract` is triggered every evening by `AgentSchedulerContract` (or on-demand for live demonstrations) and executes an autonomous multi-step reasoning loop:
1. Discovers available tools from `McpServerContract.listTools()`.
2. Calls `get_house_status(user_id)` via `McpServerContract.callTool(...)` to inspect current battery charge and whether an EV is approaching home.
3. Calls `get_forecasts(location)` via `McpServerContract.callTool(...)` using the household's coordinates.
4. Sends the telemetry and 24-hour forecast context to `LlmClientContract` to determine the optimal charge/discharge window (e.g., charging from solar midday and selling 40% of stored battery energy during the 19:00 evening tariff peak).
5. Calls `propose_battery_schedule(user_id, action_plan)` to register the `PENDING` proposal for the homeowner.

---

## 4. Sequence Diagrams (Stage 1.5)

### 4.1 Nightly Agentic Energy Optimization via MCP

```mermaid
sequenceDiagram
    autonumber
    participant Scheduler as AgentScheduler
    participant Agent as EnergyOptimizationAgent
    participant LLM as External LLM (Free Tier)
    participant MCP as Remote MCP Server
    participant Telemetry as Telemetry Service (M2)
    participant Core as Core Energy Service (M1)
    participant DB as Primary DB (SSoT)

    Scheduler->>Agent: runNightlyCommunityOptimization()
    Agent->>MCP: listTools()
    MCP-->>Agent: [get_house_status, get_forecasts, propose_battery_schedule]

    loop For each active userId
        Agent->>LLM: generateWithTools(systemPrompt + userId, tools)
        LLM-->>Agent: ToolCall: get_house_status(user_id)
        Agent->>MCP: callTool("get_house_status", {user_id})
        MCP->>Telemetry: fetchHouseStatusByUserId(userId)
        Telemetry-->>MCP: HouseStatusReadResult (battery=75%, lat/lon)
        MCP-->>Agent: McpToolResult (HouseStatusReadResult JSON)

        Agent->>LLM: generateWithTools(history + HouseStatus, tools)
        LLM-->>Agent: ToolCall: get_forecasts(location)
        Agent->>MCP: callTool("get_forecasts", {latitude, longitude})
        MCP->>Telemetry: fetchForecastForLocation(location)
        Telemetry-->>MCP: ForecastReadResult (24h solar W/m2 & EUR/kWh)
        MCP-->>Agent: McpToolResult (ForecastReadResult JSON)

        Agent->>LLM: generateWithTools(history + Forecast, tools)
        Note over LLM: Correlates solar peak with 19:00 tariff spike
        LLM-->>Agent: ToolCall: propose_battery_schedule(user_id, action_plan)

        Agent->>MCP: callTool("propose_battery_schedule", {user_id, action_plan})
        MCP->>Core: proposeBatterySchedule(userId, BatteryActionPlanDto)
        Core->>DB: INSERT BatteryProposal (status = PENDING)
        DB-->>Core: Persisted Proposal
        Core-->>MCP: BatteryProposalDto (status = PENDING)
        MCP-->>Agent: McpToolResult (ProposalSubmissionResult)

        Agent->>LLM: generateWithTools(history + ProposalResult, tools)
        LLM-->>Agent: Final optimization summary
    end
```

### 4.2 User Proposal Review & Approval in Mobile App

```mermaid
sequenceDiagram
    autonumber
    actor User as Homeowner (Mobile App - M4)
    participant Mobile as Mobile Client
    participant Core as Core Energy Service (M1)
    participant DB as Primary DB (SSoT)
    participant Broker as Message Broker (M2)

    User->>Mobile: Open Proposals Screen
    Mobile->>Core: GET /api/houses/{houseId}/proposals?status=PENDING
    Core->>DB: SELECT * FROM proposals WHERE house_id = ? AND status = 'PENDING'
    DB-->>Core: List<BatteryProposalDto>
    Core-->>Mobile: 200 OK (Pending Proposals)
    Mobile-->>User: Display "Sell 40% of battery tonight at 19:00 (+4.80 EUR)"

    alt Homeowner Accepts Proposal
        User->>Mobile: Tap "Accept"
        Mobile->>Core: POST /api/proposals/{proposalId}/accept
        Core->>DB: UPDATE proposals SET status = 'ACCEPTED' WHERE id = ?
        Core->>Broker: Publish ProposalAcceptedEvent(proposalId, houseId, actionPlan)
        Core-->>Mobile: 200 OK (BatteryProposalDto status = ACCEPTED)
    else Homeowner Rejects Proposal
        User->>Mobile: Tap "Reject"
        Mobile->>Core: POST /api/proposals/{proposalId}/reject
        Core->>DB: UPDATE proposals SET status = 'REJECTED' WHERE id = ?
        Core-->>Mobile: 200 OK (BatteryProposalDto status = REJECTED)
    end
```

### 4.3 Geolocation EV Proximity Override

```mermaid
sequenceDiagram
    autonumber
    participant GPS as Device GPS Sensor
    participant Mobile as Mobile Client (M4)
    participant Core as Core Energy Service (M1)
    participant Broker as Message Broker (M2)
    participant Telemetry as Telemetry Service (M2)

    GPS->>Mobile: Location Update (lat, lon)
    Note over Mobile: ProximityDetector computes distance < 1.5 km to House GeoPoint
    Mobile->>Core: POST /api/houses/{houseId}/proximity (event = APPROACHING_HOME)
    Core->>Broker: Publish EvProximityEvent(houseId, APPROACHING_HOME)
    Broker->>Telemetry: Consume EvProximityEvent
    Note over Telemetry,Core: Suspend outbound grid battery sales & reserve charge for EV
    Core-->>Mobile: 200 OK (Battery reserved for EV charging)
```

---

## 5. Proposal Lifecycle State Diagram

```mermaid
stateDiagram-v2
    [*] --> PENDING : MCP Tool propose_battery_schedule() invoked by AI Agent
    PENDING --> ACCEPTED : User approves in Mobile App
    PENDING --> REJECTED : User rejects in Mobile App
    PENDING --> EXPIRED : Scheduled executeAt time passes without action
    ACCEPTED --> EXPIRED : Superseded by APPROACHING_HOME EV proximity event
    ACCEPTED --> [*] : Scheduled battery action executed
    REJECTED --> [*]
    EXPIRED --> [*]
```
