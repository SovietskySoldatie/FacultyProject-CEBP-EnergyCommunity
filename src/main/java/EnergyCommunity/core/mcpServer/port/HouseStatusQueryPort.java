package EnergyCommunity.core.mcpServer.port;

import EnergyCommunity.core.mcpServer.tools.model.HouseStatusReadResult;
import java.util.List;
import java.util.Optional;

/**
 * Outbound port used by the MCP Server to read household telemetry and hardware configuration
 * from the backend services (Telemetry Service &amp; Core Energy Service).
 * <p>
 * Follows the Dependency Inversion Principle so {@code EnergyCommunity.core.mcpServer}
 * remains decoupled from internal persistence or streaming mechanisms.
 */
public interface HouseStatusQueryPort {

    /**
     * Fetches the current battery level, consumption, solar production, and location
     * for the house associated with the given user ID.
     *
     * @param userId Homeowner user ID
     * @return Optional containing the house status snapshot if the user and house exist
     */
    Optional<HouseStatusReadResult> fetchHouseStatusByUserId(String userId);

    /**
     * Lists all active homeowner user IDs enrolled in the automatic nightly optimization workflow.
     *
     * @return List of user IDs
     */
    List<String> listActiveUserIdsForOptimization();
}
