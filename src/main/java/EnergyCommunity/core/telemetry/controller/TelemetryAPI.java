package EnergyCommunity.core.telemetry.controller;

import EnergyCommunity.core.telemetry.model.AggregatedMetricsResponse;
import EnergyCommunity.core.telemetry.model.ForecastResponse;
import EnergyCommunity.core.telemetry.model.HouseStatusResponse;
import EnergyCommunity.core.telemetry.model.SimulationStatusResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

/**
 * Public API contract for the Telemetry &amp; Optimization Service.
 * <p>
 * Pure Java interface with OpenAPI documentation, independent of Spring Boot.
 */
@Tag(name = "Telemetry & Optimization API", description = "Endpoints for real-time sensor streams, 24-hour solar & price forecasts, and simulation controls")
public interface TelemetryAPI {

    @Operation(summary = "Get real-time house status", description = "Retrieves live battery charge, instantaneous consumption, and solar generation for a house")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "House status retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "House telemetry not found")
    })
    Optional<HouseStatusResponse> getHouseStatus(
            @Parameter(description = "ID of the house", required = true)
            Long houseId);

    @Operation(summary = "Get real-time status by user ID", description = "Retrieves live status snapshot for the house owned by the given user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User house status retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User or house telemetry not found")
    })
    Optional<HouseStatusResponse> getHouseStatusByUserId(
            @Parameter(description = "ID of the user", required = true)
            Long userId);

    @Operation(summary = "Get all house statuses", description = "Retrieves current real-time telemetry snapshots for all community houses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of house statuses")
    })
    List<HouseStatusResponse> getAllHouseStatuses();

    @Operation(summary = "Get 24-hour forecast", description = "Retrieves 24-hour solar radiation (W/m^2) and dynamic electricity prices (EUR/kWh) for coordinates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Forecast retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid latitude or longitude")
    })
    ForecastResponse getForecast(
            @Parameter(description = "Geographic latitude", required = true)
            Double latitude,
            @Parameter(description = "Geographic longitude", required = true)
            Double longitude);

    @Operation(summary = "Get aggregated metrics for a house", description = "Retrieves average load, peak demand, total generation, and net balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aggregated metrics retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "House not found")
    })
    Optional<AggregatedMetricsResponse> getHouseMetrics(
            @Parameter(description = "ID of the house", required = true)
            Long houseId);

    @Operation(summary = "Update EV proximity", description = "Notifies the system when mobile GPS detects user approaching home with an EV")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proximity updated successfully"),
            @ApiResponse(responseCode = "404", description = "House not found")
    })
    void updateEvProximity(
            @Parameter(description = "ID of the house", required = true)
            Long houseId,
            @Parameter(description = "True if approaching home with EV, false if away", required = true)
            boolean approaching);

    @Operation(summary = "Get simulation status", description = "Retrieves the current state and parameters of the background telemetry simulator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Simulation status retrieved successfully")
    })
    SimulationStatusResponse getSimulationStatus();

    @Operation(summary = "Start telemetry simulation", description = "Starts the background streaming simulation of sensor events")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Simulation started successfully")
    })
    SimulationStatusResponse startSimulation(
            @Parameter(description = "Tick interval in seconds", required = false)
            int intervalSeconds);

    @Operation(summary = "Stop telemetry simulation", description = "Stops the background streaming simulation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Simulation stopped successfully")
    })
    SimulationStatusResponse stopSimulation();
}
