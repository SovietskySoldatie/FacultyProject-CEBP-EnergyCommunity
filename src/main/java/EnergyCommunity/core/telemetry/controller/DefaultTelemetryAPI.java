package EnergyCommunity.core.telemetry.controller;

import EnergyCommunity.core.telemetry.model.AggregatedMetricsResponse;
import EnergyCommunity.core.telemetry.model.ForecastResponse;
import EnergyCommunity.core.telemetry.model.HouseStatusResponse;
import EnergyCommunity.core.telemetry.model.SimulationStatusResponse;
import EnergyCommunity.core.telemetry.service.ForecastService;
import EnergyCommunity.core.telemetry.service.HouseStatusService;
import EnergyCommunity.core.telemetry.service.TelemetryAggregationService;
import EnergyCommunity.core.telemetry.simulation.TelemetrySimulationService;

import java.util.List;
import java.util.Optional;

/**
 * Pure Java implementation of {@link TelemetryAPI} without any Spring Boot dependency.
 */
public class DefaultTelemetryAPI implements TelemetryAPI {

    private final HouseStatusService houseStatusService;
    private final ForecastService forecastService;
    private final TelemetryAggregationService aggregationService;
    private final TelemetrySimulationService simulationService;

    public DefaultTelemetryAPI(
            HouseStatusService houseStatusService,
            ForecastService forecastService,
            TelemetryAggregationService aggregationService,
            TelemetrySimulationService simulationService) {
        this.houseStatusService = houseStatusService;
        this.forecastService = forecastService;
        this.aggregationService = aggregationService;
        this.simulationService = simulationService;
    }

    @Override
    public Optional<HouseStatusResponse> getHouseStatus(Long houseId) {
        return houseStatusService != null ? houseStatusService.getHouseStatus(houseId) : Optional.empty();
    }

    @Override
    public Optional<HouseStatusResponse> getHouseStatusByUserId(Long userId) {
        return houseStatusService != null ? houseStatusService.getHouseStatusByUserId(userId) : Optional.empty();
    }

    @Override
    public List<HouseStatusResponse> getAllHouseStatuses() {
        return houseStatusService != null ? houseStatusService.getAllHouseStatuses() : List.of();
    }

    @Override
    public ForecastResponse getForecast(Double latitude, Double longitude) {
        return forecastService != null ? forecastService.get24HourForecast(latitude, longitude) : null;
    }

    @Override
    public Optional<AggregatedMetricsResponse> getHouseMetrics(Long houseId) {
        return aggregationService != null ? Optional.ofNullable(aggregationService.getDailyMetrics(houseId)) : Optional.empty();
    }

    @Override
    public void updateEvProximity(Long houseId, boolean approaching) {
        if (houseStatusService != null) {
            houseStatusService.setEvProximityStatus(houseId, approaching);
        }
    }

    @Override
    public SimulationStatusResponse getSimulationStatus() {
        return simulationService != null ? simulationService.getStatus() : null;
    }

    @Override
    public SimulationStatusResponse startSimulation(int intervalSeconds) {
        if (simulationService != null) {
            simulationService.startSimulation(intervalSeconds);
            return simulationService.getStatus();
        }
        return null;
    }

    @Override
    public SimulationStatusResponse stopSimulation() {
        if (simulationService != null) {
            simulationService.stopSimulation();
            return simulationService.getStatus();
        }
        return null;
    }
}
