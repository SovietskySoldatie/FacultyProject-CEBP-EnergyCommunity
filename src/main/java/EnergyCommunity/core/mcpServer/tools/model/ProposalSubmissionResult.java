package EnergyCommunity.core.mcpServer.tools.model;

/**
 * Result model returned by MCP Tool 3: {@code propose_battery_schedule(user_id, action_plan)}.
 *
 * @param acceptedBySystem True if the proposal passed validation and was registered as PENDING
 * @param proposal         The created {@link BatteryProposal}
 * @param message          Status message returned to the AI agent
 */
public record ProposalSubmissionResult(
        boolean acceptedBySystem,
        BatteryProposal proposal,
        String message
) {
}
