package EnergyCommunity.core.mcpServer.agent;

import EnergyCommunity.core.mcpServer.agent.model.OptimizationRunReport;
import java.time.LocalTime;
import java.util.List;

/**
 * Contract for the scheduled background job that triggers the {@link EnergyOptimizationAgentContract}
 * every evening to prepare next-day battery schedules for community members.
 */
public interface AgentSchedulerContract {

    /**
     * Schedules the daily evening optimization job at the specified wall-clock time.
     *
     * @param eveningTriggerTime Time of day to trigger the agent (e.g. {@code LocalTime.of(20, 0)})
     */
    void scheduleNightlyJob(LocalTime eveningTriggerTime);

    /**
     * Immediately triggers an on-demand optimization run for all active users
     * (useful for live demos and integration testing).
     *
     * @return List of optimization reports produced by the run
     */
    List<OptimizationRunReport> triggerImmediateRun();

    /**
     * Cancels the active nightly schedule and releases timer resources.
     */
    void stopScheduler();

    /**
     * Checks whether the nightly scheduler is currently active.
     *
     * @return True if scheduled and running
     */
    boolean isSchedulerActive();
}
