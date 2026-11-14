package EnergyCommunity.core.mcpServer.agent;

import EnergyCommunity.core.mcpServer.agent.model.OptimizationRunReport;
import java.util.List;

/**
 * Contract for the autonomous Agentic AI component that optimizes household energy schedules.
 * <p>
 * Unlike a passive chatbot, this agent executes a concrete multi-step workflow through the MCP Server:
 * <ol>
 *     <li>Invokes {@code get_house_status(user_id)} to inspect battery charge, capacity, and consumption.</li>
 *     <li>Invokes {@code get_forecasts(location)} to inspect 24-hour solar radiation and dynamic grid prices.</li>
 *     <li>Correlates weather forecasts with peak price windows using LLM reasoning.</li>
 *     <li>Invokes {@code propose_battery_schedule(user_id, action_plan)} to submit the most profitable
 *         {@code PENDING} schedule for the next day.</li>
 * </ol>
 */
public interface EnergyOptimizationAgentContract {

    /**
     * Runs the end-to-end MCP tool-calling loop to generate a next-day battery optimization
     * proposal for a single user.
     *
     * @param userId Target homeowner identifier
     * @return Execution report including invoked MCP tools and the generated proposal
     */
    OptimizationRunReport optimizeEnergyForUser(String userId);

    /**
     * Executes the nightly batch optimization across all active households in the energy community.
     *
     * @return List of optimization reports for each processed household
     */
    List<OptimizationRunReport> runNightlyCommunityOptimization();
}
