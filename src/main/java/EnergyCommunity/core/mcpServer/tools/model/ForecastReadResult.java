package EnergyCommunity.core.mcpServer.tools.model;

import java.time.Instant;
import java.util.List;

/**
 * Result model returned by MCP Tool 2: {@code get_forecasts(location)}.
 * Contains the 24-hour solar radiation forecast and dynamic electricity price curve.
 *
 * @param location    Geographical location for which the forecast was generated
 * @param generatedAt Timestamp when the forecast was retrieved
 * @param horizonHours Forecast window length in hours (default 24)
 * @param points      Chronological list of hourly solar radiation and energy price points
 */
public record ForecastReadResult(
        GeoLocationInput location,
        Instant generatedAt,
        int horizonHours,
        List<ForecastPointData> points
) {
}
