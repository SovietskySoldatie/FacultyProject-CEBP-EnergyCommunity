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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Telemetry & Optimization API", description = "Endpoints for real-time sensor streams, 24-hour solar & price forecasts, and simulation controls")
@RequestMapping("/api/v1/telemetry")
public interface TelemetryAPI {

    @Operation(summary = "Get real-time house status", description = "Retrieves live battery charge, instantaneous consumption, and solar generation for a house")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "House status retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "House telemetry not found")
    })
    @GetMapping("/houses/{houseId}/status")
    ResponseEntity<HouseStatusResponse> getHouseStatus(
            @Parameter(description = "ID of the house", required = true)
            @PathVariable Long houseId);

    @Operation(summary = "Get real-time status by user ID", description = "Retrieves live status snapshot for the house owned by the given user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User house status retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User or house telemetry not found")
    })
    @GetMapping("/users/{userId}/status")
    ResponseEntity<HouseStatusResponse> getHouseStatusByUserId(
            @Parameter(description = "ID of the user", required = true)
            @PathVariable Long userId);

    @Operation(summary = "Get all house statuses", description = "Retrieves current real-time telemetry snapshots for all community houses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of house statuses")
    })
    @GetMapping("/houses/status")
    ResponseEntity<List<HouseStatusResponse>> getAllHouseStatuses();

    @Operation(summary = "Get 24-hour forecast", description = "Retrieves 24-hour solar radiation (W/m^2) and dynamic electricity prices (EUR/kWh) for coordinates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Forecast retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid latitude or longitude")
    })
    @GetMapping("/forecasts")
    ResponseEntity<ForecastResponse> getForecast(
            @Parameter(description = "Geographic latitude", required = true)
            @RequestParam Double latitude,
            @Parameter(description = "Geographic longitude", required = true)
            @RequestParam Double longitude);

    @Operation(summary = "Get aggregated metrics for a house", description = "Retrieves average load, peak demand, total generation, and net balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aggregated metrics retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "House not found")
    })
    @GetMapping("/houses/{houseId}/metrics")
    ResponseEntity<AggregatedMetricsResponse> getHouseMetrics(
            @Parameter(description = "ID of the house", required = true)
            @PathVariable Long houseId);

    @Operation(summary = "Update EV proximity", description = "Notifies the system when mobile GPS detects user approaching home with an EV")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proximity updated successfully"),
            @ApiResponse(responseCode = "404", description = "House not found")
    })
    @PostMapping("/houses/{houseId}/proximity")
    ResponseEntity<Void> updateEvProximity(
            @Parameter(description = "ID of the house", required = true)
            @PathVariable Long houseId,
            @Parameter(description = "True if approaching home with EV, false if away", required = true)
            @RequestParam boolean approaching);

    @Operation(summary = "Get simulation status", description = "Retrieves the current state and parameters of the background telemetry simulator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Simulation status retrieved successfully")
    })
    @GetMapping("/simulation/status")
    ResponseEntity<SimulationStatusResponse> getSimulationStatus();

    @Operation(summary = "Start telemetry simulation", description = "Starts the background streaming simulation of sensor events")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Simulation started successfully")
    })
    @PostMapping("/simulation/start")
    ResponseEntity<SimulationStatusResponse> startSimulation(
            @Parameter(description = "Tick interval in seconds", required = false)
            @RequestParam(defaultValue = "5") int intervalSeconds);

    @Operation(summary = "Stop telemetry simulation", description = "Stops the background streaming simulation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Simulation stopped successfully")
    })
    @PostMapping("/simulation/stop")
    ResponseEntity<SimulationStatusResponse> stopSimulation();
}
