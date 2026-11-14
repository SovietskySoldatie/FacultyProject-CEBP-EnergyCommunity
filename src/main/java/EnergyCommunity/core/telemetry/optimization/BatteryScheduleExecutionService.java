package EnergyCommunity.core.telemetry.optimization;

import EnergyCommunity.messageBroker.event.ProposalAcceptedEvent;

/**
 * Service contract for managing real-time battery operational schedules,
 * grid export permissions, and EV proximity reservation overrides.
 */
public interface BatteryScheduleExecutionService {

    /**
     * Registers an AI-proposed schedule that was approved by the homeowner.
     *
     * @param event The proposal acceptance event
     */
    void registerAcceptedSchedule(ProposalAcceptedEvent event);

    /**
     * Applies an EV proximity state transition for a house.
     * When approaching, outbound grid sales are suspended and battery capacity is reserved for EV charging.
     *
     * @param houseId         Identifier of the house
     * @param approachingHome True if approaching home with EV, false if departed
     */
    void applyEvProximityOverride(Long houseId, boolean approachingHome);

    /**
     * Determines whether outbound battery power export to the grid is currently permitted for the house.
     * Export is forbidden if capacity is reserved for an approaching EV or if no active export schedule is running.
     *
     * @param houseId Identifier of the house
     * @return True if discharging to grid is permitted
     */
    boolean isGridExportAllowed(Long houseId);

    /**
     * Cancels or expires an active battery schedule for the specified house.
     *
     * @param houseId Identifier of the house
     */
    void cancelActiveSchedule(Long houseId);
}
