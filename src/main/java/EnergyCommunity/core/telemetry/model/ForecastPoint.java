package EnergyCommunity.core.telemetry.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

/**
 * Hourly forecast data point combining solar radiation irradiance and dynamic grid tariff.
 * Aligned with Mobile Client's {@code ForecastPoint} and MCP Agent's {@code ForecastPointData}.
 *
 * @param time                Target hourly timestamp
 * @param solarRadiationWm2   Predicted solar irradiance in W/m^2
 * @param pricePerKwh         Predicted dynamic electricity price in EUR/kWh
 */
@Schema(description = "Hourly forecast point combining solar irradiance and dynamic market price")
public record ForecastPoint(
        @Schema(description = "Forecast hour timestamp (ISO-8601)", example = "2026-11-15T14:00:00Z")
        Instant time,

        @Schema(description = "Predicted solar irradiance in W/m^2", example = "650.5")
        double solarRadiationWm2,

        @Schema(description = "Predicted dynamic grid price in EUR/kWh", example = "0.24")
        double pricePerKwh
) {
}
