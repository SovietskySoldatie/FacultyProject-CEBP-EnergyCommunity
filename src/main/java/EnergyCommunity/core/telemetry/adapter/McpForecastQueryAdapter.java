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

        ForecastResponse response = forecastService.get24HourForecast(lat, lon);
        List<ForecastPointData> points = response.points().stream()
                .map(this::toMcpPoint)
                .toList();

        return new ForecastReadResult(
                location != null ? location : new GeoLocationInput(lat, lon, response.regionName()),
                response.generatedAt() != null ? response.generatedAt() : Instant.now(),
                response.horizonHours(),
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
