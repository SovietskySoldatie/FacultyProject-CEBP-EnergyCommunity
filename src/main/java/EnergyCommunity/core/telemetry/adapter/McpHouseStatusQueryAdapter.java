package EnergyCommunity.core.telemetry.adapter;

import EnergyCommunity.core.mcpServer.port.HouseStatusQueryPort;
import EnergyCommunity.core.mcpServer.tools.model.GeoLocationInput;
import EnergyCommunity.core.mcpServer.tools.model.HouseStatusReadResult;
import EnergyCommunity.core.telemetry.client.HouseConfigurationProvider;
import EnergyCommunity.core.telemetry.model.HouseHardwareConfig;
import EnergyCommunity.core.telemetry.model.HouseStatusResponse;
import EnergyCommunity.core.telemetry.service.HouseStatusService;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Adapter implementing Member 3's {@link HouseStatusQueryPort} by bridging calls
 * to Member 2's {@link HouseStatusService} and {@link HouseConfigurationProvider}.
 * Pure Java implementation without Spring Boot.
 */
public class McpHouseStatusQueryAdapter implements HouseStatusQueryPort {

    private final HouseStatusService houseStatusService;
    private final HouseConfigurationProvider configProvider;

    public McpHouseStatusQueryAdapter(HouseStatusService houseStatusService, HouseConfigurationProvider configProvider) {
        this.houseStatusService = houseStatusService;
        this.configProvider = configProvider;
    }

    public McpHouseStatusQueryAdapter(HouseStatusService houseStatusService) {
        this(houseStatusService, null);
    }

    @Override
    public Optional<HouseStatusReadResult> fetchHouseStatusByUserId(String userId) {
        if (userId == null || userId.isBlank() || houseStatusService == null) {
            return Optional.empty();
        }
        try {
            Long parsedUserId = Long.parseLong(userId.trim());
            Optional<HouseStatusResponse> statusOpt = houseStatusService.getHouseStatusByUserId(parsedUserId);
            if (statusOpt.isEmpty()) {
                return Optional.empty();
            }
            HouseStatusResponse status = statusOpt.get();

            GeoLocationInput location = null;
            if (configProvider != null) {
                Optional<HouseHardwareConfig> configOpt = configProvider.getHouseConfigurationByUserId(parsedUserId);
                if (configOpt.isPresent()) {
                    HouseHardwareConfig cfg = configOpt.get();
                    location = new GeoLocationInput(cfg.latitude(), cfg.longitude(), cfg.name());
                }
            }
            if (location == null) {
                location = new GeoLocationInput(45.7489, 21.2087, "Timisoara");
            }

            HouseStatusReadResult result = new HouseStatusReadResult(
                    String.valueOf(status.userId()),
                    String.valueOf(status.houseId()),
                    location,
                    status.batteryLevelPercent(),
                    status.batteryCapacityKwh(),
                    status.currentConsumptionKw(),
                    status.currentSolarProductionKw(),
                    status.evApproachingHome(),
                    status.updatedAt() != null ? status.updatedAt() : Instant.now()
            );
            return Optional.of(result);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<String> listActiveUserIdsForOptimization() {
        if (configProvider != null) {
            List<HouseHardwareConfig> configs = configProvider.getAllActiveHouseConfigurations();
            if (configs != null && !configs.isEmpty()) {
                return configs.stream()
                        .map(HouseHardwareConfig::userId)
                        .filter(Objects::nonNull)
                        .map(String::valueOf)
                        .distinct()
                        .toList();
            }
        }
        if (houseStatusService != null) {
            return houseStatusService.getAllHouseStatuses().stream()
                    .map(HouseStatusResponse::userId)
                    .filter(Objects::nonNull)
                    .map(String::valueOf)
                    .distinct()
                    .toList();
        }
        return List.of();
    }
}
