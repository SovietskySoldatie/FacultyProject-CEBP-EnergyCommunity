package EnergyCommunity.core.telemetry.factory;

import EnergyCommunity.core.mcpServer.port.ForecastQueryPort;
import EnergyCommunity.core.mcpServer.port.HouseStatusQueryPort;
import EnergyCommunity.core.telemetry.client.HouseConfigurationProvider;
import EnergyCommunity.core.telemetry.controller.TelemetryAPI;
import EnergyCommunity.core.telemetry.service.ForecastService;
import EnergyCommunity.core.telemetry.service.HouseStatusService;
import EnergyCommunity.core.telemetry.service.TelemetryAggregationService;
import EnergyCommunity.core.telemetry.simulation.TelemetrySimulationService;

/**
 * Factory contract for instantiating and providing telemetry interfaces
 * exposed to other system components (such as Member 3's MCP Server and external clients)
 * without relying on Spring Boot dependency injection.
 */
public interface TelemetryFactory {

    /**
     * Returns the singleton instance of the default telemetry factory.
     *
     * @return The TelemetryFactory instance
     */
    static TelemetryFactory getInstance() {
        return DefaultTelemetryFactory.INSTANCE;
    }

    /**
     * Creates a {@link HouseStatusQueryPort} instance for Member 3's MCP Server.
     *
     * @param houseStatusService Service providing real-time telemetry snapshots
     * @param configProvider     Provider for household hardware configuration
     * @return HouseStatusQueryPort instance
     */
    HouseStatusQueryPort createHouseStatusQueryPort(
            HouseStatusService houseStatusService,
            HouseConfigurationProvider configProvider);

    /**
     * Convenience method to create a {@link HouseStatusQueryPort} without a config provider.
     *
     * @param houseStatusService Service providing real-time telemetry snapshots
     * @return HouseStatusQueryPort instance
     */
    default HouseStatusQueryPort createHouseStatusQueryPort(HouseStatusService houseStatusService) {
        return createHouseStatusQueryPort(houseStatusService, null);
    }

    /**
     * Creates a {@link ForecastQueryPort} instance for Member 3's MCP Server.
     *
     * @param forecastService Service providing 24-hour solar and dynamic tariff forecasts
     * @return ForecastQueryPort instance
     */
    ForecastQueryPort createForecastQueryPort(ForecastService forecastService);

    /**
     * Creates a {@link TelemetryAPI} instance for external clients and controllers.
     *
     * @param houseStatusService Service providing real-time telemetry snapshots
     * @param forecastService    Service providing 24-hour forecasts
     * @param aggregationService Service providing aggregated metrics
     * @param simulationService  Service driving the sensor data simulation
     * @return TelemetryAPI instance
     */
    TelemetryAPI createTelemetryAPI(
            HouseStatusService houseStatusService,
            ForecastService forecastService,
            TelemetryAggregationService aggregationService,
            TelemetrySimulationService simulationService);
}
