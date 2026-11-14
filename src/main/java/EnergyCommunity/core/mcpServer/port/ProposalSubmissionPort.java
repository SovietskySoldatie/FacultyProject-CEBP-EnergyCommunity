package EnergyCommunity.core.mcpServer.port;

import EnergyCommunity.core.mcpServer.tools.model.BatteryActionPlan;
import EnergyCommunity.core.mcpServer.tools.model.BatteryProposal;

/**
 * Outbound port used by the MCP Server's {@code propose_battery_schedule} tool
 * to submit a newly generated {@code PENDING} battery schedule proposal to the Core backend.
 */
public interface ProposalSubmissionPort {

    /**
     * Submits a proposed battery schedule for a user so it can be persisted with {@code PENDING} status.
     *
     * @param userId     Target homeowner user ID
     * @param actionPlan Proposed battery schedule details
     * @return The registered battery proposal
     */
    BatteryProposal submitProposal(String userId, BatteryActionPlan actionPlan);
}
