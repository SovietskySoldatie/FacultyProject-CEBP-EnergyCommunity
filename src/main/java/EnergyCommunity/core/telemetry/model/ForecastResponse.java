package EnergyCommunity.core.telemetry.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;

/**
 * 24-hour solar radiation and dynamic electricity price forecast payload.
 *
 * @param latitude     Geographic latitude of the forecast location
 * @param longitude    Geographic longitude of the forecast location
 * @param regionName   Optional human-readable region or grid zone name
 * @param generatedAt  Timestamp when the forecast was computed
 * @param horizonHours Forecast horizon window in hours (typically 24)
 * @param points       Chronological hourly forecast points
 */
@Schema(description = "24-hour weather and dynamic tariff forecast for a location")
public record ForecastResponse(
        @Schema(description = "Latitude of the forecast coordinates", example = "45.7489")
        Double latitude,

        @Schema(description = "Longitude of the forecast coordinates", example = "21.2087")
        Double longitude,

        @Schema(description = "Grid region name", example = "Timisoara-West")
        String regionName,

        @Schema(description = "Generation timestamp", example = "2026-11-14T12:00:00Z")
        Instant generatedAt,

        @Schema(description = "Forecast horizon in hours", example = "24")
        int horizonHours,

        @Schema(description = "List of hourly forecast points")
        List<ForecastPoint> points
) {
}
