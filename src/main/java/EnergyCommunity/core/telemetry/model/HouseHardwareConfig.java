package EnergyCommunity.core.telemetry.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Read-only hardware and configuration metadata snapshot for a household.
 * Used by simulation and aggregation services to compute generation and storage metrics.
 *
 * @param houseId                 Unique house identifier
 * @param userId                  Owner user identifier
 * @param name                    House label or description
 * @param latitude                Geographic latitude
 * @param longitude               Geographic longitude
 * @param totalBatteryCapacityKwh Sum of installed battery capacity in kWh
 * @param maxBatteryPowerKw       Maximum total charge/discharge power rate in kW
 * @param totalSolarCapacityKw    Sum of peak solar panel capacities in kWp
 * @param solarPanelCount         Number of installed solar panels
 * @param baselineConsumptionKw   Base average power consumption in kW
 */
@Schema(description = "Hardware and configuration metadata for a smart household")
public record HouseHardwareConfig(
        @Schema(description = "Unique house identifier", example = "1")
        Long houseId,

        @Schema(description = "Owner user identifier", example = "1")
        Long userId,

        @Schema(description = "House name or label", example = "Casa Timisoara Nord")
        String name,

        @Schema(description = "Geographic latitude", example = "45.7489")
        Double latitude,

        @Schema(description = "Geographic longitude", example = "21.2087")
        Double longitude,

        @Schema(description = "Total installed battery capacity in kWh", example = "13.5")
        double totalBatteryCapacityKwh,

        @Schema(description = "Maximum battery charge/discharge rate in kW", example = "5.0")
        double maxBatteryPowerKw,

        @Schema(description = "Total solar panel capacity in kWp", example = "6.5")
        double totalSolarCapacityKw,

        @Schema(description = "Count of installed solar panels", example = "1")
        int solarPanelCount,

        @Schema(description = "Baseline continuous electrical load in kW", example = "1.8")
        double baselineConsumptionKw
) {
}
