package EnergyCommunity.messageBroker.event;

import EnergyCommunity.core.mcpServer.tools.model.BatteryActionType;
import java.time.Instant;

/**
 * Event published when a homeowner approves an AI battery optimization schedule in the mobile app.
 * Consumed by the Telemetry &amp; Optimization Service to schedule or execute battery actions.
 *
 * @param proposalId         Unique identifier of the approved proposal
 * @param houseId            Target house identifier
 * @param userId             Homeowner identifier
 * @param actionType         Type of battery operation (e.g. SELL_TO_GRID, CHARGE_FROM_SOLAR)
 * @param batteryPercentage  Target percentage of battery capacity to allocate (0 - 100)
 * @param executeAt          Scheduled execution timestamp
 * @param estimatedProfitEur Estimated financial return in EUR
 * @param timestamp          Timestamp when the proposal was accepted
 */
public record ProposalAcceptedEvent(
        String proposalId,
        Long houseId,
        Long userId,
        BatteryActionType actionType,
        int batteryPercentage,
        Instant executeAt,
        Double estimatedProfitEur,
        Instant timestamp
) {
}
