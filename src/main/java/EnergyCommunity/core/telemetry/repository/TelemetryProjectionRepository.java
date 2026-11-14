package EnergyCommunity.core.telemetry.repository;

import EnergyCommunity.core.telemetry.model.ForecastResponse;
import EnergyCommunity.core.telemetry.model.HouseStatusResponse;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Fast-access read-projection repository for real-time telemetry and cached forecasts.
 * <p>
 * <b>Architectural SSoT Rule:</b>
 * This repository manages a decoupled secondary read store (e.g., Redis or in-memory cache)
 * populated entirely by consuming events from the message broker.
 * It <em>never</em> writes to the primary relational database (PostgreSQL),
 * respecting Member 1's role as the Single Source of Truth writer.
 */
public interface TelemetryProjectionRepository {

    /**
     * Updates or inserts the latest real-time operational status snapshot for a house.
     *
     * @param snapshot The updated house status snapshot
     */
    void saveHouseStatus(HouseStatusResponse snapshot);

    /**
     * Retrieves the latest operational telemetry snapshot for a specific house.
     *
     * @param houseId Identifier of the house
     * @return Optional containing the latest snapshot, or empty if no data received yet
     */
    Optional<HouseStatusResponse> findHouseStatusById(Long houseId);

    /**
     * Retrieves the latest operational telemetry snapshot for a house by owner user ID.
     *
     * @param userId Identifier of the owner user
     * @return Optional containing the latest snapshot
     */
    Optional<HouseStatusResponse> findHouseStatusByUserId(Long userId);

    /**
     * Lists all current house telemetry snapshots in the read projection.
     *
     * @return List of all current snapshots
     */
    List<HouseStatusResponse> findAllHouseStatuses();

    /**
     * Updates the EV proximity flag for a house when an {@code EvProximityEvent} is consumed.
     *
     * @param houseId          Identifier of the house
     * @param evApproachingHome True if the user is approaching home with an EV
     */
    void updateEvProximityStatus(Long houseId, boolean evApproachingHome);

    /**
     * Checks whether an EV is currently approaching the given house.
     *
     * @param houseId Identifier of the house
     * @return True if approaching
     */
    boolean isEvApproachingHome(Long houseId);

    /**
     * Stores a calculated or fetched 24-hour forecast in the fast cache.
     *
     * @param latitude  Geographical latitude
     * @param longitude Geographical longitude
     * @param forecast  The 24-hour forecast data
     */
    void cacheForecast(Double latitude, Double longitude, ForecastResponse forecast);

    /**
     * Retrieves a cached 24-hour forecast for the specified coordinates.
     *
     * @param latitude  Geographical latitude
     * @param longitude Geographical longitude
     * @return Optional containing the cached forecast if present and not expired
     */
    Optional<ForecastResponse> findCachedForecast(Double latitude, Double longitude);
}
