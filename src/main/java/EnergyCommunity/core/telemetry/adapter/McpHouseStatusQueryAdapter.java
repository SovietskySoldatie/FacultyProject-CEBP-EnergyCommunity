package EnergyCommunity.core.telemetry.adapter;

import EnergyCommunity.core.mcpServer.port.HouseStatusQueryPort;
import EnergyCommunity.core.mcpServer.tools.model.GeoLocationInput;
import EnergyCommunity.core.mcpServer.tools.model.HouseStatusReadResult;
import EnergyCommunity.core.telemetry.model.HouseStatusResponse;
import EnergyCommunity.core.telemetry.service.HouseStatusService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Adapter implementing Member 3's {@link HouseStatusQueryPort} by bridging calls
 * to Member 2's {@link HouseStatusService}.
 */
public class McpHouseStatusQueryAdapter implements HouseStatusQueryPort {

    private final HouseStatusService houseStatusService;

    public McpHouseStatusQueryAdapter(HouseStatusService houseStatusService) {
        this.houseStatusService = houseStatusService;
    }

    @Override
    public Optional<HouseStatusReadResult> fetchHouseStatusByUserId(String userId) {
        try {
            Long parsedUserId = Long.parseLong(userId);
            return houseStatusService.getHouseStatusByUserId(parsedUserId)
                    .map(this::toMcpModel);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<String> listActiveUserIdsForOptimization() {
        return houseStatusService.getAllHouseStatuses().stream()
                .map(HouseStatusResponse::userId)
                .map(String::valueOf)
                .distinct()
                .toList();
    }

    private HouseStatusReadResult toMcpModel(HouseStatusResponse response) {
        GeoLocationInput location = new GeoLocationInput(45.7489, 21.2087, "Timisoara");
        return new HouseStatusReadResult(
                String.valueOf(response.userId()),
                String.valueOf(response.houseId()),
                location,
                response.batteryLevelPercent(),
                response.batteryCapacityKwh(),
                response.currentConsumptionKw(),
                response.currentSolarProductionKw(),
                response.evApproachingHome(),
                response.updatedAt() != null ? response.updatedAt() : Instant.now()
        );
    }
}
