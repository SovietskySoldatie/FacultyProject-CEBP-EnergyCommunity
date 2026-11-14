package EnergyCommunity.core.mcpServer.tools;

import EnergyCommunity.core.mcpServer.protocol.McpTool;
import EnergyCommunity.core.mcpServer.tools.model.BatteryActionPlan;
import EnergyCommunity.core.mcpServer.tools.model.ProposalSubmissionResult;

/**
 * Contract for <b>MCP Tool 3 (Propose Change)</b>: {@code propose_battery_schedule(user_id, action_plan)}.
 * <p>
 * Generates a {@code PENDING} battery optimization schedule in the system
 * (e.g. "Sell 40% of battery tonight at 19:00"), making it visible in the mobile
 * application for user approval or rejection.
 */
public interface ProposeBatteryScheduleToolContract extends McpTool {

    String TOOL_NAME = "propose_battery_schedule";

    /**
     * Strongly-typed invocation of the {@code propose_battery_schedule(user_id, action_plan)} MCP tool.
     *
     * @param userId     Target homeowner identifier
     * @param actionPlan Proposed battery schedule and AI reasoning
     * @return Submission status and the generated {@code PENDING} proposal
     */
    ProposalSubmissionResult proposeBatterySchedule(String userId, BatteryActionPlan actionPlan);
}
