package EnergyCommunity.core.mcpServer.agent.model;

import EnergyCommunity.core.mcpServer.tools.model.BatteryProposal;
import java.time.Instant;
import java.util.List;

/**
 * Summary report produced by the Agentic AI component after completing an optimization task
 * for a single user.
 *
 * @param userId             Target homeowner user ID
 * @param executedAt         Timestamp when the agentic workflow finished
 * @param success            True if the agent successfully analyzed data and generated a proposal
 * @param invokedTools       Ordered list of MCP tool names invoked by the agent during reasoning
 * @param generatedProposal  The resulting {@code PENDING} battery schedule proposal (if created)
 * @param agentSummary       Natural-language explanation of the optimization strategy
 */
public record OptimizationRunReport(
        String userId,
        Instant executedAt,
        boolean success,
        List<String> invokedTools,
        BatteryProposal generatedProposal,
        String agentSummary
) {
}
