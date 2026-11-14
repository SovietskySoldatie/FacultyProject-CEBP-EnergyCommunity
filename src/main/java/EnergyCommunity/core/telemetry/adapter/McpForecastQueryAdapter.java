package EnergyCommunity.core.telemetry.adapter;

import EnergyCommunity.core.mcpServer.port.ForecastQueryPort;
import EnergyCommunity.core.mcpServer.tools.model.ForecastPointData;
import EnergyCommunity.core.mcpServer.tools.model.ForecastReadResult;
import EnergyCommunity.core.mcpServer.tools.model.GeoLocationInput;
import EnergyCommunity.core.telemetry.model.ForecastPoint;
import EnergyCommunity.core.telemetry.model.ForecastResponse;
import EnergyCommunity.core.telemetry.service.ForecastService;

import java.time.Instant;
import java.util.List;

/**
 * Adapter implementing Member 3's {@link ForecastQueryPort} by bridging calls
 * to Member 2's {@link ForecastService}.
 * Pure Java implementation without Spring Boot.
 */
public class McpForecastQueryAdapter implements ForecastQueryPort {

    private final ForecastService forecastService;

    public McpForecastQueryAdapter(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @Override
    public ForecastReadResult fetchForecastForLocation(GeoLocationInput location) {
        double lat = location != null ? location.latitude() : 45.7489;
        double lon = location != null ? location.longitude() : 21.2087;

        ForecastResponse response = (forecastService != null)
                ? forecastService.get24HourForecast(lat, lon)
                : null;

        List<ForecastPointData> points = (response != null && response.points() != null)
                ? response.points().stream().map(this::toMcpPoint).toList()
                : List.of();

        String regionName = (location != null && location.regionName() != null && !location.regionName().isBlank())
                ? location.regionName()
                : (response != null && response.regionName() != null ? response.regionName() : "Timisoara");

        return new ForecastReadResult(
                new GeoLocationInput(lat, lon, regionName),
                (response != null && response.generatedAt() != null) ? response.generatedAt() : Instant.now(),
                (response != null) ? response.horizonHours() : 24,
                points
        );
    }

    private ForecastPointData toMcpPoint(ForecastPoint point) {
        return new ForecastPointData(
                point.time(),
                point.solarRadiationWm2(),
                point.pricePerKwh()
        );
    }
}
