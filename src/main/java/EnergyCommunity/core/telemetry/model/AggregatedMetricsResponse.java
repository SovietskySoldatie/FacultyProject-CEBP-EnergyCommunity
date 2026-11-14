package EnergyCommunity.core.telemetry.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

/**
 * Aggregated energy metrics computed over a specified time window.
 *
 * @param houseId                  Unique identifier of the house
 * @param averageConsumptionKw     Average power demand in kW over the interval
 * @param peakConsumptionKw        Peak instantaneous power demand in kW
 * @param totalConsumptionKwh      Total energy consumed in kWh
 * @param totalSolarProductionKwh  Total solar energy generated in kWh
 * @param netEnergyBalanceKwh      Net grid balance in kWh (production minus consumption)
 * @param periodStart              Beginning of the aggregation window
 * @param periodEnd                End of the aggregation window
 */
@Schema(description = "Aggregated energy consumption and production statistics")
public record AggregatedMetricsResponse(
        @Schema(description = "Unique identifier of the house", example = "1")
        Long houseId,

        @Schema(description = "Average power demand in kW", example = "2.1")
        double averageConsumptionKw,

        @Schema(description = "Peak instantaneous power demand in kW", example = "6.4")
        double peakConsumptionKw,

        @Schema(description = "Total energy consumed in kWh", example = "18.5")
        double totalConsumptionKwh,

        @Schema(description = "Total solar energy generated in kWh", example = "24.2")
        double totalSolarProductionKwh,

        @Schema(description = "Net energy balance in kWh (positive = net export, negative = net import)", example = "5.7")
        double netEnergyBalanceKwh,

        @Schema(description = "Start of aggregation period", example = "2026-11-14T00:00:00Z")
        Instant periodStart,

        @Schema(description = "End of aggregation period", example = "2026-11-14T23:59:59Z")
        Instant periodEnd
) {
}
