package EnergyCommunity.core.telemetry.factory;

import EnergyCommunity.core.mcpServer.port.ForecastQueryPort;
import EnergyCommunity.core.mcpServer.port.HouseStatusQueryPort;
import EnergyCommunity.core.telemetry.adapter.McpForecastQueryAdapter;
import EnergyCommunity.core.telemetry.adapter.McpHouseStatusQueryAdapter;
import EnergyCommunity.core.telemetry.client.HouseConfigurationProvider;
import EnergyCommunity.core.telemetry.controller.DefaultTelemetryAPI;
import EnergyCommunity.core.telemetry.controller.TelemetryAPI;
import EnergyCommunity.core.telemetry.service.ForecastService;
import EnergyCommunity.core.telemetry.service.HouseStatusService;
import EnergyCommunity.core.telemetry.service.TelemetryAggregationService;
import EnergyCommunity.core.telemetry.simulation.TelemetrySimulationService;

/**
 * Default implementation of {@link TelemetryFactory} providing instances
 * of telemetry interfaces exposed to other components without Spring Boot.
 */
public class DefaultTelemetryFactory implements TelemetryFactory {

    public static final DefaultTelemetryFactory INSTANCE = new DefaultTelemetryFactory();

    public DefaultTelemetryFactory() {
    }

    @Override
    public HouseStatusQueryPort createHouseStatusQueryPort(
            HouseStatusService houseStatusService,
            HouseConfigurationProvider configProvider) {
        return new McpHouseStatusQueryAdapter(houseStatusService, configProvider);
    }

    @Override
    public ForecastQueryPort createForecastQueryPort(ForecastService forecastService) {
        return new McpForecastQueryAdapter(forecastService);
    }

    @Override
    public TelemetryAPI createTelemetryAPI(
            HouseStatusService houseStatusService,
            ForecastService forecastService,
            TelemetryAggregationService aggregationService,
            TelemetrySimulationService simulationService) {
        return new DefaultTelemetryAPI(
                houseStatusService,
                forecastService,
                aggregationService,
                simulationService
        );
    }
}
