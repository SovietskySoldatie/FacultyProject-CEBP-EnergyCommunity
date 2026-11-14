package EnergyCommunity.core.telemetry.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

/**
 * Real-time operational snapshot of a household in the energy community.
 * Consumed by both the Mobile Client and the Model Context Protocol (MCP) Server.
 *
 * @param houseId                  Unique house identifier
 * @param userId                   Unique owner identifier
 * @param batteryLevelPercent      Current battery state of charge (0 - 100%)
 * @param batteryCapacityKwh       Total installed battery storage capacity in kWh
 * @param currentConsumptionKw     Instantaneous household power demand in kW
 * @param currentSolarProductionKw Instantaneous rooftop solar generation in kW
 * @param evApproachingHome        True if mobile geolocation indicates EV approaching home
 * @param updatedAt                Timestamp of the latest telemetry event received
 */
@Schema(description = "Real-time snapshot of household energy metrics, battery state, and EV proximity")
public record HouseStatusResponse(
        @Schema(description = "Unique identifier of the house", example = "1")
        Long houseId,

        @Schema(description = "Unique identifier of the owner user", example = "1")
        Long userId,

        @Schema(description = "Battery state of charge in percentage (0 - 100)", example = "78")
        int batteryLevelPercent,

        @Schema(description = "Total battery capacity in kWh", example = "13.5")
        double batteryCapacityKwh,

        @Schema(description = "Instantaneous household power consumption in kW", example = "1.85")
        double currentConsumptionKw,

        @Schema(description = "Instantaneous solar panel power generation in kW", example = "4.20")
        double currentSolarProductionKw,

        @Schema(description = "Indicates whether mobile GPS detected the user approaching home with an EV", example = "false")
        boolean evApproachingHome,

        @Schema(description = "Timestamp of the last telemetry update", example = "2026-11-14T14:30:00Z")
        Instant updatedAt
) {
}
