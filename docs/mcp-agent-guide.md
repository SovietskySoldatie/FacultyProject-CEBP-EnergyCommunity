# MCP Server & Agentic AI Guide (Member 3)

## Overview

The `EnergyCommunity.core.mcpServer` package implements the **Model Context Protocol (MCP) Server** and the **Autonomous Energy Optimization Agent** for the Energy Community platform.

### Package Structure

```text
src/main/java/EnergyCommunity/core/mcpServer/
├── agent/
│   ├── model/
│   │   ├── LlmMessage.java
│   │   ├── LlmResponse.java
│   │   ├── LlmToolCall.java
│   │   └── OptimizationRunReport.java
│   ├── AgentSchedulerContract.java
│   ├── EnergyOptimizationAgentContract.java
│   └── LlmClientContract.java
├── port/
│   ├── ForecastQueryPort.java
│   ├── HouseStatusQueryPort.java
│   └── ProposalSubmissionPort.java
├── protocol/
│   ├── McpServerContract.java
│   ├── McpServerInfo.java
│   ├── McpTool.java
│   ├── McpToolCallRequest.java
│   ├── McpToolRegistry.java
│   ├── McpToolResult.java
│   └── McpToolSchema.java
└── tools/
    ├── model/
    │   ├── BatteryActionPlan.java
    │   ├── BatteryActionType.java
    │   ├── BatteryProposal.java
    │   ├── ForecastPointData.java
    │   ├── ForecastReadResult.java
    │   ├── GeoLocationInput.java
    │   ├── HouseStatusReadResult.java
    │   ├── ProposalStatus.java
    │   └── ProposalSubmissionResult.java
    ├── GetForecastsToolContract.java
    ├── GetHouseStatusToolContract.java
    └── ProposeBatteryScheduleToolContract.java
```

---

## Exposed MCP Tools

| Tool Name | Type | Input Arguments | Output Model | Purpose |
| :--- | :--- | :--- | :--- | :--- |
| `get_house_status` | **Read** | `user_id` (`String`) | `HouseStatusReadResult` | Retrieves battery level (%), battery capacity (kWh), current household load (kW), solar output (kW), and EV proximity status. |
| `get_forecasts` | **Read** | `location` (`latitude`, `longitude`, `regionName`) | `ForecastReadResult` | Retrieves 24-hour solar radiation ($\text{W/m}^2$) and dynamic grid prices (EUR/kWh). |
| `propose_battery_schedule` | **Propose Change** | `user_id` (`String`), `action_plan` (`BatteryActionPlan`) | `ProposalSubmissionResult` | Creates a `PENDING` battery optimization proposal via `ProposalSubmissionPort` for user review in the mobile app. |
