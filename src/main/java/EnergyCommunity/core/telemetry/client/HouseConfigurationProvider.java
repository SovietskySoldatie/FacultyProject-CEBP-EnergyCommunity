package EnergyCommunity.core.telemetry.client;

import EnergyCommunity.core.telemetry.model.HouseHardwareConfig;
import java.util.List;
import java.util.Optional;

/**
 * Read-only client provider contract for accessing household hardware configurations
 * from Core Energy Service without violating the Single Source of Truth (SSoT) boundary.
 */
public interface HouseConfigurationProvider {

    /**
     * Retrieves the hardware configuration (battery capacity, solar panels, coordinates) for a given house.
     *
     * @param houseId Identifier of the house
     * @return Optional containing hardware configuration if found
     */
    Optional<HouseHardwareConfig> getHouseConfiguration(Long houseId);

    /**
     * Retrieves the hardware configuration for the house owned by the given user.
     *
     * @param userId Identifier of the owner user
     * @return Optional containing hardware configuration if found
     */
    Optional<HouseHardwareConfig> getHouseConfigurationByUserId(Long userId);

    /**
     * Retrieves all active household hardware configurations in the community.
     *
     * @return List of active configurations
     */
    List<HouseHardwareConfig> getAllActiveHouseConfigurations();
}
