package EnergyCommunity.core.telemetry.service;

import EnergyCommunity.core.telemetry.model.HouseStatusResponse;

import java.util.List;
import java.util.Optional;

/**
 * Service contract for querying live household telemetry and battery status.
 * Used by the Telemetry REST Controller, the MCP Server, and the AI Optimizer.
 */
public interface HouseStatusService {

    /**
     * Retrieves the current real-time status of a house by its ID.
     *
     * @param houseId Identifier of the house
     * @return Optional containing the house status if available
     */
    Optional<HouseStatusResponse> getHouseStatus(Long houseId);

    /**
     * Retrieves the current real-time status of the house owned by the given user.
     *
     * @param userId Identifier of the homeowner user
     * @return Optional containing the house status if found
     */
    Optional<HouseStatusResponse> getHouseStatusByUserId(Long userId);

    /**
     * Retrieves the live status for all registered households.
     *
     * @return List of all house statuses
     */
    List<HouseStatusResponse> getAllHouseStatuses();

    /**
     * Updates the EV proximity status for a house, overriding battery discharge behavior.
     *
     * @param houseId          Identifier of the house
     * @param evApproachingHome True if the user is approaching home with an EV
     */
    void setEvProximityStatus(Long houseId, boolean evApproachingHome);
}
