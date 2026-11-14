package EnergyCommunity.core.telemetry.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

/**
 * Status of the background sensor telemetry simulation engine.
 *
 * @param running              True if simulation is actively publishing events to the broker
 * @param simulatedHouseCount  Number of households currently being simulated
 * @param tickIntervalSeconds  Interval in seconds between simulated sensor ticks
 * @param lastTickAt           Timestamp of the most recent simulation tick
 */
@Schema(description = "Status of the background sensor telemetry simulation engine")
public record SimulationStatusResponse(
        @Schema(description = "Indicates whether the simulator is actively running", example = "true")
        boolean running,

        @Schema(description = "Number of houses configured for simulation", example = "5")
        int simulatedHouseCount,

        @Schema(description = "Simulation tick frequency in seconds", example = "5")
        int tickIntervalSeconds,

        @Schema(description = "Timestamp of the last simulation tick", example = "2026-11-14T14:35:10Z")
        Instant lastTickAt
) {
}
