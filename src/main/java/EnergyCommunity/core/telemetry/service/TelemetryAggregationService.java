package EnergyCommunity.core.telemetry.service;

import EnergyCommunity.core.telemetry.model.AggregatedMetricsResponse;

import java.time.Instant;

/**
 * Service contract for computing rolling averages, daily totals, and aggregated statistics
 * from high-frequency telemetry event streams.
 */
public interface TelemetryAggregationService {

    /**
     * Calculates aggregated consumption, production, and net balance metrics for a house
     * over a custom time window.
     *
     * @param houseId Identifier of the house
     * @param from    Start of the interval
     * @param to      End of the interval
     * @return Aggregated metrics summary
     */
    AggregatedMetricsResponse getAggregatedMetrics(Long houseId, Instant from, Instant to);

    /**
     * Calculates daily aggregated metrics for a house for the past 24 hours.
     *
     * @param houseId Identifier of the house
     * @return 24-hour aggregated metrics summary
     */
    AggregatedMetricsResponse getDailyMetrics(Long houseId);
}
