package EnergyCommunity.core.mcpServer.tools.model;

import java.time.Instant;

/**
 * Data model representing an action plan submitted by the AI agent
 * via the {@code propose_battery_schedule(user_id, action_plan)} MCP tool.
 *
 * @param houseId            Identifier of the target house (optional if resolved via userId)
 * @param actionType         The type of battery operation proposed
 * @param batteryPercentage  Target percentage of battery capacity to allocate/sell (0-100)
 * @param executeAt          Scheduled timestamp for executing the action
 * @param estimatedProfitEur Estimated financial gain or savings in EUR
 * @param description        Human-readable summary displayed in the mobile notification
 * @param reasoning          AI agent's explanation correlating weather forecast and dynamic energy prices
 */
public record BatteryActionPlan(
        String houseId,
        BatteryActionType actionType,
        int batteryPercentage,
        Instant executeAt,
        Double estimatedProfitEur,
        String description,
        String reasoning
) {
}
