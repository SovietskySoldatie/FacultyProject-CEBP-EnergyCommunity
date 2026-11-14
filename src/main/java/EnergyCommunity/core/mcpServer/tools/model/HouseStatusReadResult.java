package EnergyCommunity.core.mcpServer.tools.model;

import java.time.Instant;

/**
 * Result model returned by MCP Tool 1: {@code get_house_status(user_id)}.
 * Combines live telemetry metrics (battery level, consumption, solar production)
 * with household hardware metadata so the AI agent has full context for optimization.
 *
 * @param userId                   User identifier of the homeowner
 * @param houseId                  Unique house identifier
 * @param location                 Geographical coordinates of the house
 * @param batteryLevelPercent      Current state of charge of the home battery (0-100%)
 * @param batteryCapacityKwh       Total usable capacity of the home battery in kWh
 * @param currentConsumptionKw     Instantaneous household power demand in kW
 * @param currentSolarProductionKw Instantaneous solar panel output in kW
 * @param evApproachingHome        True if mobile geolocation detected the user approaching home with an EV
 * @param updatedAt                Timestamp of the latest telemetry reading
 */
public record HouseStatusReadResult(
        String userId,
        String houseId,
        GeoLocationInput location,
        int batteryLevelPercent,
        double batteryCapacityKwh,
        double currentConsumptionKw,
        double currentSolarProductionKw,
        boolean evApproachingHome,
        Instant updatedAt
) {
}
